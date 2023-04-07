package co.cp.orderly.payment.data.payment.entity

import co.cp.orderly.domain.vos.PaymentStatus
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "payments")
open class PaymentEntity(
    @Id
    open var paymentId: UUID?,
    open var customerId: UUID?,
    open var orderId: UUID?,
    open var price: BigDecimal?,
    @Enumerated(EnumType.STRING)
    open var paymentStatus: PaymentStatus?,
    open var createdAt: ZonedDateTime?
) {
    constructor() : this(
        null, null, null, null,
        null, null,
    )

    override fun equals(other: Any?): Boolean {
        other as PaymentEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> paymentId == other.paymentId
        }
    }

    override fun hashCode(): Int = paymentId.hashCode()
}
