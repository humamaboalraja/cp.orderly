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
    private val approvalConsistencyDataMapper: ApprovalConsistencyDataLayerDataMapper

) : ApprovalConsistencyRepository {

    override fun save(
        orderApprovalConsistencyMessage: OrderApprovalConsistencyMessage
    ): OrderApprovalConsistencyMessage =
        approvalConsistencyDataMapper.approvalConsistencyEntityToOrderApprovalConsistencyMessage(
            approvalConsistencyPersistenceRepository.save(
                approvalConsistencyDataMapper.orderCreatedConsistencyMessageToConsistencyEntity(orderApprovalConsistencyMessage)
            )
        )

    override fun findByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState?
    ): List<OrderApprovalConsistencyMessage> =
        approvalConsistencyPersistenceRepository.findByTypeAndConsistencyStatusAndLltStatusIn(
            type, consistencyState, listOf(*lltState)
        )!!.map(approvalConsistencyDataMapper::approvalConsistencyEntityToOrderApprovalConsistencyMessage)
            .toList().also {
                if (it == null) {
                    throw ApprovalConsistencyNotFoundException(
                        "Approval consistency object couldn't be found for llt #$type"
                    )
                }
            }

    override fun findByTypeAndLltIdAndLltState(
        type: String?,
        lltId: UUID?,
        vararg lltState: LongRunningTransactionState
    ): OrderApprovalConsistencyMessage =
        approvalConsistencyDataMapper.approvalConsistencyEntityToOrderApprovalConsistencyMessage(
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
