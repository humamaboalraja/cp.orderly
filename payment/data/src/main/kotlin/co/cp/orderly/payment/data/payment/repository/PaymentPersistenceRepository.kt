package co.cp.orderly.payment.data.payment.repository

import co.cp.orderly.payment.data.payment.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentPersistenceRepository : JpaRepository<PaymentEntity, UUID> {
    fun findByOrderId(orderId: UUID): PaymentEntity?
}
