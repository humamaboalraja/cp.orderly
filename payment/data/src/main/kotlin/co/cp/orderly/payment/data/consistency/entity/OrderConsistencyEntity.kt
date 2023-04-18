package co.cp.orderly.payment.data.consistency.entity

import ConsistencyState
import co.cp.orderly.domain.vos.PaymentStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.ZonedDateTime
import java.util.Objects
import java.util.UUID

@Entity
@Table(name = "order_consistency")
open class OrderConsistencyEntity(

    @Id
    val orderConsistencyId: UUID? = null,
    val lltId: UUID? = null,
    val createdAt: ZonedDateTime? = null,
    val processedAt: ZonedDateTime? = null,
    val type: String? = null,
    val payload: String? = null,
    @Enumerated(EnumType.STRING)
    val consistencyStatus: ConsistencyState? = null,
    @Enumerated(EnumType.STRING)
    val paymentStatus: PaymentStatus? = null,
    @Version
    val version: Int? = 0
) {
    override fun equals(other: Any?): Boolean {
        other as OrderConsistencyEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> orderConsistencyId == other.orderConsistencyId
        }
    }

    override fun hashCode(): Int = Objects.hash(orderConsistencyId)
}
