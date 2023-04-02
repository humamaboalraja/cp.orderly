package co.cp.orderly.payment.data.credit.entry.mapper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.payment.data.credit.entry.entity.CreditEntryEntity
import co.cp.orderly.payment.domain.core.entity.CreditEntry
import co.cp.orderly.payment.domain.core.vos.CreditEntryId
import org.springframework.stereotype.Component

@Component
class CreditEntryDataMapper {

    fun mapCreditEntryToCreditEntryEntity(creditEntry: CreditEntry): CreditEntryEntity =
        CreditEntryEntity(
            creditEntry.getId()?.getValue(),
            creditEntry.customerId.getValue(),
            creditEntry.totalCreditAmount.getAmount()
        )

    fun mapCreditEntryEntityToCreditEntry(creditEntryEntity: CreditEntryEntity): CreditEntry =
        CreditEntry(
            CreditEntryId(creditEntryEntity.creditEntryId!!),
            CustomerId(creditEntryEntity.customerId!!),
            Money(creditEntryEntity.totalCreditAmount!!)
        )
}
