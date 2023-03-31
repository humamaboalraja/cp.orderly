package co.cp.orderly.payment.domain.service.ports.outptut.repository

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.payment.domain.core.entity.CreditHistory

interface ICreditHistoryRepository {
    fun save(creditHistory: CreditHistory): CreditHistory
    fun findByCustomerId(customerId: CustomerId): List<CreditHistory>?
}
