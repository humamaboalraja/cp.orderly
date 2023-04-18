package co.cp.orderly.payment.domain.service.consistency.scheduler

import ConsistencyState
import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.infrastructure.transactions.llt.contstants.LongRunningTransactionConstants.ORDER_LLT_NAME
import co.cp.orderly.payment.domain.core.exception.PaymentDomainException
import co.cp.orderly.payment.domain.service.consistency.model.OrderConsistencyMessage
import co.cp.orderly.payment.domain.service.consistency.model.OrderEventPayloadDTO
import co.cp.orderly.payment.domain.service.ports.outptut.repository.IOrderConsistencyRepository
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import java.util.logging.Logger

@Component
open class OrderConsistencyUtil(
    private val orderConsistencyRepository: IOrderConsistencyRepository,
    private val objectMapper: ObjectMapper,

) {

    companion object { private val logger = Logger.getLogger(OrderConsistencyUtil::class.java.name) }

    @Transactional(readOnly = true)
    open fun getCompletedOrderConsistencyMessageByLltIdAndPaymentStatus(
        lltId: UUID?,
        paymentStatus: PaymentStatus?
    ) =
        orderConsistencyRepository.findByTypeAndLltIdAndPaymentStatusAndConsistencyState(
            ORDER_LLT_NAME, lltId,
            paymentStatus, ConsistencyState.COMPLETED
        )

    @Transactional(readOnly = true)
    open fun getOrderConsistencyMessageByConsistencyStatus(consistencyState: ConsistencyState?) =
        orderConsistencyRepository.findByTypeAndConsistencyState(ORDER_LLT_NAME, consistencyState!!)

    @Transactional
    open fun deleteOrderConsistencyMessageByConsistencyStatus(consistencyState: ConsistencyState) =
        orderConsistencyRepository.deleteByTypeAndConsistencyState(ORDER_LLT_NAME, consistencyState)

    @Transactional
    open fun saveOrderConsistencyMessage(
        orderEventPayload: OrderEventPayloadDTO,
        paymentStatus: PaymentStatus?,
        consistencyState: ConsistencyState?,
        lltId: UUID?
    ) = save(
        OrderConsistencyMessage(
            UUID.randomUUID(),
            lltId!!,
            orderEventPayload.createdAt,
            ZonedDateTime.now(ZoneId.of("UTC")),
            ORDER_LLT_NAME,
            createPayload(orderEventPayload)!!,
            paymentStatus!!,
            consistencyState!!
        )
    )

    @Transactional
    open fun updateConsistencyMessage(orderConsistencyMessage: OrderConsistencyMessage, consistencyStatus: ConsistencyState) {
        orderConsistencyMessage.consistencyStatus = consistencyStatus
        save(orderConsistencyMessage)
        logger.info(
            "Order consistency table status is updated as: #${consistencyStatus.name}",

        )
    }

    private fun createPayload(orderEventPayload: OrderEventPayloadDTO) =
        try {
            objectMapper.writeValueAsString(orderEventPayload)
        } catch (e: JsonProcessingException) {
            logger.info("Couldn't create OrderEventPayloadDTO json! $e")
            throw PaymentDomainException("Couldn't create OrderEventPayloadDTO json", e)
        }

    private fun save(orderConsistencyMessage: OrderConsistencyMessage) {
        val response = orderConsistencyRepository.save(orderConsistencyMessage)
        if (response == null) {
            logger.info("Couldn't save OrderConsistencyMessage")
            throw PaymentDomainException("Couldn't save OrderConsistencyMessage")
        }
        logger.info(
            "OrderConsistencyMessage is saved with id: #${orderConsistencyMessage.id}",
        )
    }
}
