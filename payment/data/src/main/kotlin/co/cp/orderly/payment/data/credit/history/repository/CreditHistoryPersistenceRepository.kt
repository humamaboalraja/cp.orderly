package co.cp.orderly.payment.data.credit.history.repository

import co.cp.orderly.payment.data.credit.history.entity.CreditHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CreditHistoryPersistenceRepository : JpaRepository<CreditHistoryEntity, UUID> {
    fun findByCustomerId(customerId: UUID): List<CreditHistoryEntity>?
}
