package co.cp.orderly.shop.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.OrderApprovalStatus
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.shop.domain.core.vos.OrderApprovalId

class OrderApproval private constructor(
    val orderApprovalId: OrderApprovalId?,
    var shopId: ShopId?,
    var orderId: OrderId?,
    var orderApprovalStatus: OrderApprovalStatus?,

) : BaseEntity<OrderApprovalId>() {

    init { orderApprovalId?.let { super.setId(it) } }

    companion object { fun builder(): Builder = Builder() }

    data class Builder(
        var orderApprovalId: OrderApprovalId? = null,
        var shopId: ShopId? = null,
        var orderId: OrderId? = null,
        var orderApprovalStatus: OrderApprovalStatus? = null,
    ) {
        fun orderApprovalId(orderApprovalId: OrderApprovalId?) = apply { this.orderApprovalId = orderApprovalId }
        fun shopId(shopId: ShopId?) = apply { this.shopId = shopId }
        fun orderId(orderId: OrderId?) = apply { this.orderId = orderId }
        fun orderApprovalStatus(orderApprovalStatus: OrderApprovalStatus?) = apply { this.orderApprovalStatus = orderApprovalStatus }
        fun build() = OrderApproval(
            orderApprovalId, shopId, orderId, orderApprovalStatus
        )
    }
}
