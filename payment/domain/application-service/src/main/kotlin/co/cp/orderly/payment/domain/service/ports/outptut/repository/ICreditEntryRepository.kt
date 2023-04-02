package co.cp.orderly.payment.domain.service.ports.outptut.repository

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.payment.domain.core.entity.CreditEntry

interface ICreditEntryRepository {
    fun save(creditEntry: CreditEntry): CreditEntry
    fun findByCustomerId(customerId: CustomerId): CreditEntry?
}
