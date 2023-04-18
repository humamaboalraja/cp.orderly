package co.cp.orderly.order.data.consisntecy.payment.repository

import ConsistencyState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.data.consisntecy.payment.entity.PaymentConsistencyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentConsistencyPersistenceRepository : JpaRepository<PaymentConsistencyEntity, UUID> {
    fun findByTypeAndConsistencyStateAndLltStateIn(
        type: String?,
        consistencyState: ConsistencyState?,
        lltStatus: List<LongRunningTransactionState>?
    ): List<PaymentConsistencyEntity>?

    fun findByTypeAndLltIdAndLltStateIn(
        type: String?,
        lltId: UUID?,
        lltStatus: List<LongRunningTransactionState?>?
    ): PaymentConsistencyEntity?

    fun deleteByTypeAndConsistencyStateAndLongLltIn(
        type: String?,
        consistencyState: ConsistencyState?,
        lltStatus: List<LongRunningTransactionState>?
    )
}
