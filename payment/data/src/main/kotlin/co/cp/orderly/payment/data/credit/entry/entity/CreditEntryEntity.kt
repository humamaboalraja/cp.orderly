package co.cp.orderly.payment.data.credit.entry.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "credit_entries")
open class CreditEntryEntity(
    @Id
    open var creditEntryId: UUID?,
    open var customerId: UUID?,
    open var totalCreditAmount: BigDecimal?,
    open var createdAt: ZonedDateTime? = null
) {
    constructor() : this(
        null, null, null, null,
    )

    override fun equals(other: Any?): Boolean {
        other as CreditEntryEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> creditEntryId == other.creditEntryId
        }
    }

    override fun hashCode(): Int = creditEntryId.hashCode()
}
