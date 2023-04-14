package co.cp.orderly.order.domain.application.service.service.llt.order

import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionStep
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import co.cp.orderly.order.domain.application.service.consistency.scheduler.approval.ApprovalConsistencyUtil
import co.cp.orderly.order.domain.application.service.consistency.scheduler.payment.PaymentConsistencyUtil
import co.cp.orderly.order.domain.application.service.dto.message.payment.PaymentResponseDTO
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.application.service.utils.dataMapper.OrderApplicationServiceDataMapper
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.event.OrderPaidEvent
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import co.cp.orderly.order.domain.core.exception.OrderNotFoundException
import co.cp.orderly.order.domain.core.service.IOrderDomainService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import java.util.logging.Logger

@Component
open class OrderPaymentLlt(
    private val orderDomainService: IOrderDomainService,
    private val orderRepository: OrderRepository,
    private val paymentConsistencyUtil: PaymentConsistencyUtil,
    private val approvalConsistencyUtil: ApprovalConsistencyUtil,
    private val orderLltUtil: OrderLltUtil,
    private val orderDataMapper: OrderApplicationServiceDataMapper
) : LongRunningTransactionStep<PaymentResponseDTO> {

    companion object { private val logger = Logger.getLogger(OrderPaymentLlt::class.java.name) }

    @Transactional
    override fun process(paymentResponse: PaymentResponseDTO) {
        val orderPaymentConsistencyMessageResponse: OrderPaymentConsistencyMessage =
            paymentConsistencyUtil.getPaymentConsistencyMessageByLltIdAndLltState(
                UUID.fromString(paymentResponse.sagaId),
                LongRunningTransactionState.STARTED
            )
        if (orderPaymentConsistencyMessageResponse == null) {
            logger.info("An outbox message with saga #${paymentResponse.sagaId} is already processed")
            return
        }
        val domainEvent = completePaymentForOrder(paymentResponse)
        val lltState = orderLltUtil.orderStatusToLltStatus(domainEvent.order.orderStatus)
        paymentConsistencyUtil.save(
            getUpdatedPaymentConsistencyMessage(orderPaymentConsistencyMessageResponse, domainEvent.order.orderStatus!!, lltState)
        )
        approvalConsistencyUtil
            .saveApprovalConsistencyMessage(
                orderDataMapper.orderPaidEventToOrderApprovalEventPayload(domainEvent)!!,
                domainEvent.order.orderStatus!!, lltState, ConsistencyState.STARTED,
                UUID.fromString(
                    paymentResponse.sagaId
                )
            )
        logger.info("Order #${domainEvent.order.getId()?.getValue()} is paid")
    }

    @Transactional
    override fun rollback(paymentResponse: PaymentResponseDTO) {
        val orderPaymentConsistencyMessageResponse =
            paymentConsistencyUtil.getPaymentConsistencyMessageByLltIdAndLltState(
                UUID.fromString(paymentResponse.sagaId),
                *getCurrentLltStatus(paymentResponse.paymentStatus)
            )
        if (orderPaymentConsistencyMessageResponse == null) {
            logger.info("Outbox message with saga id #${paymentResponse.sagaId} is already rollbacked")
            return
        }
        val order = rollbackPaymentForOrder(paymentResponse)
        val lltState = orderLltUtil.orderStatusToLltStatus(order.orderStatus)
        paymentConsistencyUtil.save(
            getUpdatedPaymentConsistencyMessage(orderPaymentConsistencyMessageResponse, order.orderStatus!!, lltState)
        )
        if (paymentResponse.paymentStatus === PaymentStatus.CANCELLED) {
            approvalConsistencyUtil.save(
                getUpdatedApprovalConsistencyMessage(paymentResponse.sagaId, order.orderStatus!!, lltState)
            )
        }
        logger.info("Order #${order.getId()?.getValue()} is cancelled")
    }

    private fun findOrder(orderId: String): Order {
        val orderResponse = orderRepository.findById(OrderId(UUID.fromString(orderId)))
        if (orderResponse == null) {
            logger.info("Order # $orderId couldn't be found")
            throw OrderNotFoundException("Order with id $orderId could not be found!")
        }
        return orderResponse
    }

    private fun getUpdatedPaymentConsistencyMessage(
        orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage,
        orderStatus: OrderStatus,
        lltStatus: LongRunningTransactionState
    ): OrderPaymentConsistencyMessage {
        orderPaymentConsistencyMessage.processedAt = ZonedDateTime.now(ZoneId.of("UTC"))
        orderPaymentConsistencyMessage.orderStatus = orderStatus
        orderPaymentConsistencyMessage.lltStatus = lltStatus
        return orderPaymentConsistencyMessage
    }

    private fun completePaymentForOrder(paymentResponse: PaymentResponseDTO): OrderPaidEvent {
        logger.info("Completing order #${paymentResponse.orderId}'s payment")
        val order = findOrder(paymentResponse.orderId)
        val domainEvent = orderDomainService.payOrder(order)
        orderRepository.saveOrder(order)
        return domainEvent
    }

    private fun getCurrentLltStatus(paymentStatus: PaymentStatus): Array<LongRunningTransactionState> =
        when (paymentStatus) {
            PaymentStatus.COMPLETED -> arrayOf(LongRunningTransactionState.STARTED)
            PaymentStatus.CANCELLED -> arrayOf(LongRunningTransactionState.PROCESSING)
            PaymentStatus.FAILED -> arrayOf(LongRunningTransactionState.STARTED, LongRunningTransactionState.PROCESSING)
        }

    private fun rollbackPaymentForOrder(paymentResponse: PaymentResponseDTO): Order {
        logger.info("Cancelling order id #${paymentResponse.orderId}")
        val order = findOrder(paymentResponse.orderId)
        orderDomainService.cancelOrder(order, paymentResponse.errorMessages)
        orderRepository.saveOrder(order)
        return order
    }

    private fun getUpdatedApprovalConsistencyMessage(
        lltId: String,
        orderStatus: OrderStatus,
        lltState: LongRunningTransactionState
    ): OrderApprovalConsistencyMessage {
        val orderApprovalConsistencyMessageResponse =
            approvalConsistencyUtil.getApprovalConsistencyMessageByLltIdAndLltState(
                UUID.fromString(lltId),
                LongRunningTransactionState.COMPENSATING
            )
        if (orderApprovalConsistencyMessageResponse == null) {
            throw OrderDomainException(
                "Approval outbox message could not be found in " +
                    LongRunningTransactionState.COMPENSATING.name + " status!"
            )
        }
        orderApprovalConsistencyMessageResponse.processedAt = ZonedDateTime.now(ZoneId.of("UTC"))
        orderApprovalConsistencyMessageResponse.orderStatus = orderStatus
        orderApprovalConsistencyMessageResponse.lltStatus = lltState
        return orderApprovalConsistencyMessageResponse
    }
}
