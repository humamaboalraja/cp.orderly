package co.cp.orderly.payment.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.payment.domain.core.vos.CreditEntryId

class CreditEntry(
    var creditEntryId: CreditEntryId,
    var customerId: CustomerId,
    var totalCreditAmount: Money
) : BaseEntity<CreditEntryId>() {
    init { setId(creditEntryId) }

    fun addAmount(amount: Money) = run { totalCreditAmount = totalCreditAmount.addMoney(amount) }
    fun subtractAmount(amount: Money) = run { totalCreditAmount = totalCreditAmount.subtractMoney(amount) }
}
