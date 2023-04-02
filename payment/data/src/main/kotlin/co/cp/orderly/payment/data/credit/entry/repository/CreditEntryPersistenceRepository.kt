package co.cp.orderly.payment.data.credit.entry.repository

import co.cp.orderly.payment.data.credit.entry.entity.CreditEntryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CreditEntryPersistenceRepository : JpaRepository<CreditEntryEntity, UUID> {
    fun findByCustomerId(customerId: UUID): CreditEntryEntity?
}
