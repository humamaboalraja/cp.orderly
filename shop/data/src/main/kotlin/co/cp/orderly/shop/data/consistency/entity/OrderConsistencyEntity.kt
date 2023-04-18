package co.cp.orderly.shop.data.consistency.entity

import ConsistencyState
import co.cp.orderly.domain.vos.OrderApprovalStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime
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
    val consistencyState: ConsistencyState? = null,
    @Enumerated(EnumType.STRING)
    val approvalStatus: OrderApprovalStatus? = null,
    val version: Int? = null
) {

    override fun equals(other: Any?): Boolean {
        other as OrderConsistencyEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> orderConsistencyId == other.orderConsistencyId
        }
    }

    override fun hashCode(): Int = orderConsistencyId?.hashCode() ?: 0
}
