package co.cp.orderly.shop.application.service.consistency.scheduler

import ConsistencyState
import co.cp.orderly.domain.vos.OrderApprovalStatus
import co.cp.orderly.infrastructure.transactions.llt.contstants.LongRunningTransactionConstants.ORDER_LLT_NAME
import co.cp.orderly.shop.application.service.consistency.model.OrderConsistencyMessage
import co.cp.orderly.shop.application.service.consistency.model.OrderEventPayloadDTO
import co.cp.orderly.shop.application.service.ports.output.repository.IOrderConsistencyRepository
import co.cp.orderly.shop.domain.core.exception.ShopDomainException
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
    val orderConsistencyRepository: IOrderConsistencyRepository,
    private val objectMapper: ObjectMapper,
) {

    companion object { private val logger = Logger.getLogger(OrderConsistencyUtil::class.java.name) }

    @Transactional(readOnly = true)
    open fun getCompletedOrderConsistencyMessageByLltIdAndConsistencyState(
        lltId: UUID?,
        consistencyStatus: ConsistencyState?
    ) =
        orderConsistencyRepository.findByTypeAndLltIdAndConsistencyState(ORDER_LLT_NAME, lltId, consistencyStatus)

    @Transactional(readOnly = true)
    open fun getOrderConsistencyMessageByConsistencyState(consistencyStatus: ConsistencyState?) =
        orderConsistencyRepository.findByTypeAndConsistencyState(ORDER_LLT_NAME, consistencyStatus)

    @Transactional
    open fun deleteOrderConsistencyMessageByConsistencyState(consistencyStatus: ConsistencyState?) =
        orderConsistencyRepository.deleteByTypeAndConsistencyState(ORDER_LLT_NAME, consistencyStatus)

    @Transactional
    open fun saveOrderConsistencyMessage(
        orderEventPayload: OrderEventPayloadDTO,
        approvalStatus: OrderApprovalStatus?,
        consistencyStatus: ConsistencyState?,
        lltId: UUID?
    ) = save(
        OrderConsistencyMessage(
            UUID.randomUUID(),
            lltId!!,
            orderEventPayload.createdAt,
            ZonedDateTime.now(ZoneId.of("UTC")),
            ORDER_LLT_NAME,
            createPayloadObject(orderEventPayload)!!,
            approvalStatus = approvalStatus!!,
            consistencyState = consistencyStatus!!
        )
    )

    @Transactional
    open fun updateConsistencyState(
        orderPaymentConsistencyMessage: OrderConsistencyMessage,
        consistencyStatus: ConsistencyState
    ) {
        orderPaymentConsistencyMessage.consistencyState = consistencyStatus
        save(orderPaymentConsistencyMessage)
        logger.info("Order consistency table status #${consistencyStatus.name} has been updated ")
    }

    private fun save(orderPaymentConsistencyMessage: OrderConsistencyMessage) {
        orderConsistencyRepository.save(orderPaymentConsistencyMessage)
            ?: throw ShopDomainException("Couldn't save OrderConsistencyMessage")
        logger.info(
            "OrderConsistencyMessage #${orderPaymentConsistencyMessage.id} has been saved"
        )
    }

    private fun createPayloadObject(orderEventPayload: OrderEventPayloadDTO) =
        try {
            objectMapper.writeValueAsString(orderEventPayload)
        } catch (exception: JsonProcessingException) {
            logger.info("Couldn't create OrderEventPayloadDTO json object. Exception #$exception")
            throw ShopDomainException("Couldn't create OrderEventPayloadDTO json object. Exception", exception)
        }
}
