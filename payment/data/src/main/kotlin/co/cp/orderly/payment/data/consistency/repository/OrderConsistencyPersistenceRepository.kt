package co.cp.orderly.payment.data.consistency.repository

import ConsistencyState
import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.payment.data.consistency.entity.OrderConsistencyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderConsistencyPersistenceRepository : JpaRepository<OrderConsistencyEntity, UUID> {
    fun findByTypeAndConsistencyState(type: String?, consistencyStatus: ConsistencyState?): List<OrderConsistencyEntity>?

    fun findByTypeAndLltIdAndPaymentStatusAndConsistencyState(
        type: String?,
        lltId: UUID?,
        paymentStatus: PaymentStatus?,
        consistencyStatus: ConsistencyState?
    ): OrderConsistencyEntity?

    fun deleteByTypeAndConsistencyState(type: String?, consistencyStatus: ConsistencyState?)
}
