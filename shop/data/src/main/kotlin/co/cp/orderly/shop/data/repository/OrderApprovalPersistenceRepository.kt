package co.cp.orderly.shop.data.repository

import co.cp.orderly.shop.data.entity.OrderApprovalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderApprovalPersistenceRepository : JpaRepository<OrderApprovalEntity, UUID>
