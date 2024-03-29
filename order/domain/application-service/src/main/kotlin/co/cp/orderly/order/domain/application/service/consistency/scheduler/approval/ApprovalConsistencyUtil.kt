package co.cp.orderly.order.domain.application.service.consistency.scheduler.approval

import ConsistencyState
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.infrastructure.transactions.llt.contstants.LongRunningTransactionConstants.ORDER_LLT_NAME
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalEventDTO
import co.cp.orderly.order.domain.application.service.ports.output.repository.ApprovalConsistencyRepository
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.logging.Logger

@Component
open class ApprovalConsistencyUtil(
    val approvalConsistencyRepository: ApprovalConsistencyRepository,
    private val objectMapper: ObjectMapper
) {

    companion object { private val logger = Logger.getLogger(ApprovalConsistencyUtil::class.java.name) }

    @Transactional(readOnly = true)
    open fun getApprovalConsistencyMessageByConsistencyStateAndLltState(
        consistencyStatus: ConsistencyState?,
        vararg lltState: LongRunningTransactionState?
    ): List<OrderApprovalConsistencyMessage> =
        approvalConsistencyRepository.findByTypeAndConsistencyStateAndLltState(
            ORDER_LLT_NAME, consistencyStatus, *lltState
        )

    @Transactional(readOnly = true)
    open fun getApprovalConsistencyMessageByLltIdAndLltState(
        lltId: UUID?,
        vararg lltState: LongRunningTransactionState
    ): OrderApprovalConsistencyMessage =
        approvalConsistencyRepository.findByTypeAndLltIdAndLltState(ORDER_LLT_NAME, lltId, *lltState)

    @Transactional
    open fun save(orderApprovalConsistencyMessage: OrderApprovalConsistencyMessage) {
        val response: OrderApprovalConsistencyMessage = approvalConsistencyRepository.save(orderApprovalConsistencyMessage)!!
        if (response == null) {
            logger.info(
                "Couldn't save OrderApprovalConsistencyMessage. id #${orderApprovalConsistencyMessage.id}"
            )
            throw OrderDomainException(
                "Couldn't save OrderApprovalConsistencyMessage. id #${orderApprovalConsistencyMessage.id}"
            )
        }
        logger.info(
            "OrderApprovalConsistencyMessage ${orderApprovalConsistencyMessage.id} has been saved"
        )
    }

    @Transactional
    open fun saveApprovalConsistencyMessage(
        orderApprovalEventDTO: OrderApprovalEventDTO,
        orderStatus: OrderStatus,
        lltState: LongRunningTransactionState,
        consistencyStatus: ConsistencyState,
        lltId: UUID
    ) = save(
        OrderApprovalConsistencyMessage(
            UUID.randomUUID(),
            lltId,
            orderApprovalEventDTO.createdAt,
            type = ORDER_LLT_NAME,
            payload = createPayload(orderApprovalEventDTO),
            orderStatus = orderStatus,
            lltStatus = lltState,
            consistencyState = consistencyStatus
        )
    )

    @Transactional
    open fun deleteApprovalConsistencyMessageByConsistencyStateAndLltState(
        consistencyStatus: ConsistencyState?,
        vararg lltState: LongRunningTransactionState
    ) =
        approvalConsistencyRepository
            .deleteByTypeAndConsistencyStateAndLltState(ORDER_LLT_NAME, consistencyStatus, *lltState)

    private fun createPayload(orderApprovalEventDTO: OrderApprovalEventDTO): String =
        try { objectMapper.writeValueAsString(orderApprovalEventDTO) } catch (exception: JsonProcessingException) {
            logger.info(
                "Couldn't create OrderApprovalEventPayload - order: #${orderApprovalEventDTO.orderId}. $exception"
            )
            throw OrderDomainException(
                "Couldn't create OrderApprovalEventPayload - order #${orderApprovalEventDTO.orderId}. $exception"
            )
        }
}
