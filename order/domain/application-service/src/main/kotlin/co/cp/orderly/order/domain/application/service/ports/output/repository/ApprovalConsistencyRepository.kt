package co.cp.orderly.order.domain.application.service.ports.output.repository

import ConsistencyState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import java.util.UUID

interface ApprovalConsistencyRepository {
    fun save(orderApprovalOutboxMessage: OrderApprovalConsistencyMessage): OrderApprovalConsistencyMessage?

    fun findByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState?
    ): List<OrderApprovalConsistencyMessage>

    fun findByTypeAndLltIdAndLltState(
        type: String?,
        lltId: UUID?,
        vararg lltState: LongRunningTransactionState
    ): OrderApprovalConsistencyMessage

    fun deleteByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState
    )
}
