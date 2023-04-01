package co.cp.orderly.payment.data.credit.history.entity

import co.cp.orderly.payment.domain.core.vos.TransactionType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "credit_histories")
open class CreditHistoryEntity(
    @Id
    open var creditHistoryId: UUID?,
    open var customerId: UUID?,
    open var amount: BigDecimal?,
    @Enumerated(EnumType.STRING)
    open var transactionType: TransactionType?,
    open var createdAt: ZonedDateTime? = null
) {
    constructor() : this(
        null, null, null, null,
        null,
    )

    override fun equals(other: Any?): Boolean {
        other as CreditHistoryEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> creditHistoryId == other.creditHistoryId
        }
    }

    override fun hashCode(): Int = creditHistoryId.hashCode()
}
