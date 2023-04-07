package co.cp.orderly.shop.data.entity

import co.cp.orderly.domain.vos.OrderApprovalStatus
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table
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
