package co.cp.orderly.order.data.order.repository

import co.cp.orderly.order.data.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderPersistenceRepository : JpaRepository<OrderEntity, UUID> {
    fun findByTrackingId(tackingId: UUID): OrderEntity?
}
