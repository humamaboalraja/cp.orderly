package co.cp.orderly.shop.data.consistency.repository

import ConsistencyState
import co.cp.orderly.shop.data.consistency.entity.OrderConsistencyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderConsistencyPersistenceRepository : JpaRepository<OrderConsistencyEntity, UUID> {

    fun findByTypeAndConsistencyState(type: String?, consistencyStatus: ConsistencyState?): List<OrderConsistencyEntity>?

    fun findByTypeAndLltIdAndConsistencyState(
        type: String?,
        lltId: UUID?,
        consistencyState: ConsistencyState?
    ): OrderConsistencyEntity?

    fun deleteByTypeAndConsistencyState(type: String?, consistencyStatus: ConsistencyState?)
}
