package co.cp.orderly.shop.data.entity

import co.cp.orderly.domain.vos.OrderApprovalStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "order_approval", schema = "shop")
open class OrderApprovalEntity(
    @Id
    open val orderApprovalId: UUID?,
    open val shopId: UUID?,
    open val orderId: UUID?,
    @Enumerated(EnumType.STRING)
    open val status: OrderApprovalStatus?,

) {
    constructor() : this(null, null, null, null,)
}
