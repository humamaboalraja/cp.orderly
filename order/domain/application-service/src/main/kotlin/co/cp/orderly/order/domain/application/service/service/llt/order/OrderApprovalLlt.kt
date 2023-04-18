package co.cp.orderly.order.domain.application.service.service.llt.order

import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import co.cp.orderly.order.domain.application.service.consistency.scheduler.approval.ApprovalConsistencyUtil
import co.cp.orderly.order.domain.application.service.consistency.scheduler.payment.PaymentConsistencyUtil
import co.cp.orderly.order.domain.application.service.dto.message.shop.ShopApprovalResponseDTO
import co.cp.orderly.order.domain.application.service.utils.dataMapper.OrderApplicationServiceDataMapper
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.event.OrderCancelledEvent
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import co.cp.orderly.order.domain.core.service.IOrderDomainService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import java.util.logging.Logger

@Component

open class OrderApprovalLlt(
    private val orderDomainService: IOrderDomainService,
    private val orderLltUtil: OrderLltUtil,
    private val paymentConsistencyUtil: PaymentConsistencyUtil,
    private val approvalConsistencyUtil: ApprovalConsistencyUtil,
    private val orderDataMapper: OrderApplicationServiceDataMapper,
) {
    companion object { private val logger = Logger.getLogger(OrderApprovalLlt::class.java.name) }

    @Transactional
    open fun process(shopApprovalResponse: ShopApprovalResponseDTO) {
        val orderApprovalConsistencyMessageResponse = approvalConsistencyUtil
            .getApprovalConsistencyMessageByLltIdAndLltState(
                UUID.fromString(shopApprovalResponse.lltId), LongRunningTransactionState.PROCESSING
            )
        if (orderApprovalConsistencyMessageResponse == null) {
            logger.info("Consistency message with llt id: ${shopApprovalResponse.lltId} is already")
            return
        }
        val order = approveOrder(shopApprovalResponse)
        val lltStatus = orderLltUtil.orderStatusToLltStatus(order.orderStatus)
        approvalConsistencyUtil.save(
            getUpdatedApprovalConsistencyMessage(orderApprovalConsistencyMessageResponse, order.orderStatus!!, lltStatus)
        )
        paymentConsistencyUtil.save(
            getUpdatedPaymentConsistencyMessage(shopApprovalResponse.lltId, order.orderStatus!!, lltStatus)
        )
        logger.info("Order id #${order.getId()?.getValue()} is approved")
    }

    @Transactional
    open fun rollback(shopApprovalResponse: ShopApprovalResponseDTO) {
        val orderApprovalConsistencyMessageResponse = approvalConsistencyUtil
            .getApprovalConsistencyMessageByLltIdAndLltState(
                UUID.fromString(shopApprovalResponse.lltId), LongRunningTransactionState.PROCESSING
            )
        if (orderApprovalConsistencyMessageResponse == null) {
            logger.info("Consistency message llt  ${shopApprovalResponse.lltId} is already rollbacked",)
            return
        }
        val domainEvent = rollbackOrder(shopApprovalResponse)
        val lltStatus = orderLltUtil.orderStatusToLltStatus(domainEvent.order.orderStatus)
        approvalConsistencyUtil.save(
            getUpdatedApprovalConsistencyMessage(
                orderApprovalConsistencyMessageResponse, domainEvent.order.orderStatus!!, lltStatus
            )
        )
        paymentConsistencyUtil.savePaymentConsistencyMessage(
            orderDataMapper.orderCancelledEventToOrderPaymentEventPayload(domainEvent),
            domainEvent.order.orderStatus!!, lltStatus, ConsistencyState.STARTED,
            UUID.fromString(
                shopApprovalResponse.lltId
            )
        )
        logger.info("Order id #${domainEvent.order.getId()?.getValue()} is canceling")
    }

    private fun approveOrder(shopApprovalResponse: ShopApprovalResponseDTO): Order {
        logger.info("Approving order id #${shopApprovalResponse.orderId}")
        val order = orderLltUtil.findOrder(shopApprovalResponse.orderId)
        orderDomainService.approveOrder(order)
        orderLltUtil.saveOrder(order)
        return order
    }

    private fun getUpdatedApprovalConsistencyMessage(
        orderApprovalConsistencyMessage: OrderApprovalConsistencyMessage,
        orderStatus: OrderStatus,
        lltStatus: LongRunningTransactionState
    ): OrderApprovalConsistencyMessage {
        orderApprovalConsistencyMessage.processedAt = ZonedDateTime.now(ZoneId.of("UTC"))
        orderApprovalConsistencyMessage.orderStatus = orderStatus
        orderApprovalConsistencyMessage.lltStatus = lltStatus
        return orderApprovalConsistencyMessage
    }

    private fun getUpdatedPaymentConsistencyMessage(
        lltId: String,
        orderStatus: OrderStatus,
        lltStatus: LongRunningTransactionState
    ): OrderPaymentConsistencyMessage {
        val orderPaymentConsistencyMessageResponse = paymentConsistencyUtil
            .getPaymentConsistencyMessageByLltIdAndLltState(UUID.fromString(lltId), LongRunningTransactionState.PROCESSING)
        if (orderPaymentConsistencyMessageResponse == null) {
            throw OrderDomainException(
                "Payment consistency message cannot be found in ${LongRunningTransactionState.PROCESSING.name} state"
            )
        }
        orderPaymentConsistencyMessageResponse.processedAt = ZonedDateTime.now(ZoneId.of("UTC"))
        orderPaymentConsistencyMessageResponse.orderStatus = orderStatus
        orderPaymentConsistencyMessageResponse.lltStatus = lltStatus
        return orderPaymentConsistencyMessageResponse
    }

    private fun rollbackOrder(shopApprovalResponse: ShopApprovalResponseDTO): OrderCancelledEvent {
        logger.info("Cancelling order with id: ${shopApprovalResponse.orderId}")
        val order = orderLltUtil.findOrder(shopApprovalResponse.orderId)
        val domainEvent: OrderCancelledEvent = orderDomainService.cancelOrderPayment(
            order, shopApprovalResponse.errorMessages
        )
        orderLltUtil.saveOrder(order)
        return domainEvent
    }
}
