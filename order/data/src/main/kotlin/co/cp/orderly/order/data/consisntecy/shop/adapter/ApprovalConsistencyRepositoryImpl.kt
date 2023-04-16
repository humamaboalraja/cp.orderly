package co.cp.orderly.order.data.consisntecy.shop.adapter

import ConsistencyState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.data.consisntecy.shop.exception.ApprovalConsistencyNotFoundException
import co.cp.orderly.order.data.consisntecy.shop.mapper.ApprovalConsistencyDataLayerDataMapper
import co.cp.orderly.order.data.consisntecy.shop.repository.ApprovalConsistencyPersistenceRepository
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import co.cp.orderly.order.domain.application.service.ports.output.repository.ApprovalConsistencyRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ApprovalConsistencyRepositoryImpl(
    private val approvalConsistencyPersistenceRepository: ApprovalConsistencyPersistenceRepository,
    private val approvalOutboxDataAccessMapper: ApprovalConsistencyDataLayerDataMapper

) : ApprovalConsistencyRepository {

    override fun save(
        orderApprovalConsistencyMessage: OrderApprovalConsistencyMessage
    ): OrderApprovalConsistencyMessage =
        approvalOutboxDataAccessMapper.approvalOutboxEntityToOrderApprovalOutboxMessage(
            approvalConsistencyPersistenceRepository.save(
                approvalOutboxDataAccessMapper.orderCreatedOutboxMessageToOutboxEntity(orderApprovalConsistencyMessage)
            )
        )

    override fun findByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState?
    ): List<OrderApprovalConsistencyMessage> =
        approvalConsistencyPersistenceRepository.findByTypeAndConsistencyStatusAndLltStatusIn(
            type, consistencyState, listOf(*lltState)
        )!!.map(approvalOutboxDataAccessMapper::approvalOutboxEntityToOrderApprovalOutboxMessage)
            .toList().also {
                if (it == null) {
                    throw ApprovalConsistencyNotFoundException("Approval outbox objectcould be found for saga type #$type")
                }
            }

    override fun findByTypeAndLltIdAndLltState(
        type: String?,
        lltId: UUID?,
        vararg lltState: LongRunningTransactionState
    ): OrderApprovalConsistencyMessage =
        approvalOutboxDataAccessMapper.approvalOutboxEntityToOrderApprovalOutboxMessage(
            approvalConsistencyPersistenceRepository
                .findByTypeAndLltIdAndLltStatusIn(type, lltId, listOf(*lltState))!!
        )

    override fun deleteByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState
    ) = approvalConsistencyPersistenceRepository.deleteByTypeAndConsistencyStatusAndLltStatusIn(
        type, consistencyState, listOf(*lltState)
    )
}
