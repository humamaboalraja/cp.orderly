package co.cp.orderly.payment.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.payment.domain.core.vos.CreditHistoryId
import co.cp.orderly.payment.domain.core.vos.TransactionType

class CreditHistory(
    val creditHistoryId: CreditHistoryId,
    val customerId: CustomerId,
    val amount: Money,
    val transactionType: TransactionType
) : BaseEntity<CreditHistoryId>() {
    init { setId(creditHistoryId) }
}
