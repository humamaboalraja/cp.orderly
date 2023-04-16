package co.cp.orderly.order.data.consisntecy.shop.repository

import ConsistencyState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.data.consisntecy.shop.entity.ApprovalConsistencyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ApprovalConsistencyPersistenceRepository : JpaRepository<ApprovalConsistencyEntity, UUID> {

    fun findByTypeAndConsistencyStatusAndLltStatusIn(
        type: String?,
        outboxStatus: ConsistencyState?,
        sagaStatus: List<LongRunningTransactionState?>?
    ): List<ApprovalConsistencyEntity>?

    fun findByTypeAndLltIdAndLltStatusIn(
        type: String?,
        sagaId: UUID?,
        sagaStatus: List<LongRunningTransactionState?>?
    ): ApprovalConsistencyEntity?

    fun deleteByTypeAndConsistencyStatusAndLltStatusIn(
        type: String?,
        outboxStatus: ConsistencyState?,
        sagaStatus: List<LongRunningTransactionState>
    )
}
