package co.cp.orderly.order.domain.application.service.consistency.scheduler.payment

import ConsistencyState
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.infrastructure.transactions.llt.contstants.LongRunningTransactionConstants.ORDER_LLT_NAME
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentEventDTO
import co.cp.orderly.order.domain.application.service.ports.output.repository.PaymentConsistencyRepository
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.logging.Logger

@Component
open class PaymentConsistencyUtil(
    private val paymentConsistencyRepository: PaymentConsistencyRepository,
    private val objectMapper: ObjectMapper?
) {

    companion object { private val logger = Logger.getLogger(PaymentConsistencyUtil::class.java.name) }

    @Transactional(readOnly = true)
    open fun getPaymentConsistencyMessageByConsistencyStateAndLltState(
        consistencyStatus: ConsistencyState?,
        vararg lltStatus: LongRunningTransactionState
    ) = paymentConsistencyRepository.findByTypeAndConsistencyStateAndLltState(
        ORDER_LLT_NAME, consistencyStatus, *lltStatus
    )

    @Transactional(readOnly = true)
    open fun getPaymentConsistencyMessageByLltIdAndLltState(
        lltId: UUID?,
        vararg lltStatus: LongRunningTransactionState
    ): OrderPaymentConsistencyMessage =
        paymentConsistencyRepository.findByTypeAndLltIdAndLltState(ORDER_LLT_NAME, lltId, *lltStatus)

    @Transactional
    open fun save(orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage) {
        val response = paymentConsistencyRepository.save(orderPaymentConsistencyMessage)!!
        if (response == null) {
            logger.info("Couldn't save OrderPaymentConsistencyMessage id #${orderPaymentConsistencyMessage.id}")
            throw OrderDomainException(
                "Couldn't save OrderPaymentConsistencyMessage id #${orderPaymentConsistencyMessage.id}"
            )
        }
        logger.info(
            "OrderPaymentConsistencyMessage #${orderPaymentConsistencyMessage.id} has been saved"
        )
    }

    @Transactional
    open fun savePaymentConsistencyMessage(
        paymentEventPayload: OrderPaymentEventDTO,
        orderStatus: OrderStatus,
        lltStatus: LongRunningTransactionState,
        consistencyStatus: ConsistencyState,
        lltId: UUID
    ) = save(
        OrderPaymentConsistencyMessage(
            id = UUID.randomUUID(),
            lltId = lltId,
            createdAt = paymentEventPayload.createdAt,
            type = ORDER_LLT_NAME,
            payload = createPayload(paymentEventPayload),
            orderStatus = orderStatus,
            lltStatus = lltStatus,
            consistencyState = consistencyStatus
        )
    )

    @Transactional
    open fun deletePaymentConsistencyMessageByConsistencyStateAndLltState(
        consistencyStatus: ConsistencyState?,
        vararg lltStatus: LongRunningTransactionState
    ) = paymentConsistencyRepository.deleteByTypeAndConsistencyStateAndLltState(
        ORDER_LLT_NAME, consistencyStatus, *lltStatus
    )

    private fun createPayload(paymentEventPayload: OrderPaymentEventDTO) =
        try {
            objectMapper!!.writeValueAsString(paymentEventPayload)
        } catch (exception: JsonProcessingException) {
            logger.info(
                "Couldn't create OrderPaymentEventPayload for order #${paymentEventPayload.orderId}" +
                    "Exception: $exception"
            )
            throw OrderDomainException(
                "Couldn't create OrderPaymentEventPayload for order #${paymentEventPayload.orderId}" +
                    "Exception: $exception"
            )
        }
}
