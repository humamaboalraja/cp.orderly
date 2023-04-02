package co.cp.orderly.payment.data.credit.history.mapper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.payment.data.credit.history.entity.CreditHistoryEntity
import co.cp.orderly.payment.domain.core.entity.CreditHistory
import co.cp.orderly.payment.domain.core.vos.CreditHistoryId
import org.springframework.stereotype.Component

@Component
class CreditHistoryDataMapper {

    fun mapCreditHistoryToCreditHistoryEntity(creditHistory: CreditHistory): CreditHistoryEntity =
        CreditHistoryEntity(
            creditHistory.getId()?.getValue(),
            creditHistory.customerId.getValue(),
            creditHistory.amount.getAmount(),
            creditHistory.transactionType
        )

    fun mapCreditHistoryEntityToCreditHistory(creditHistoryEntity: CreditHistoryEntity): CreditHistory =
        CreditHistory(
            CreditHistoryId(creditHistoryEntity.creditHistoryId!!),
            CustomerId(creditHistoryEntity.customerId!!),
            Money(creditHistoryEntity.amount!!),
            creditHistoryEntity.transactionType!!
        )
}
