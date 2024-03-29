package co.cp.orderly.order.data.consisntecy.payment.entity

import ConsistencyState
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "payment_consistency")
open class PaymentConsistencyEntity(
    @Id
    val id: UUID? = null,
    val lltId: UUID? = null,
    val createdAt: ZonedDateTime? = null,
    val processedAt: ZonedDateTime? = null,
    val type: String? = null,
    val payload: String? = null,
    @Enumerated(EnumType.STRING)
    val lltStatus: LongRunningTransactionState? = null,
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus? = null,
    @Enumerated(EnumType.STRING)
    val consistencyState: ConsistencyState? = null,
    @Version
    val version: Int? = 0
) {
    override fun equals(other: Any?): Boolean {
        other as PaymentConsistencyEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> id == other.id
        }
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
