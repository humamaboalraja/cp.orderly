package co.cp.orderly.shop.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus

class OrderData private constructor(
    val orderId: OrderId?,
    val orderStatus: OrderStatus?,
    val totalAmount: Money?,
    val products: List<Product>?,
) : BaseEntity<OrderId>() {

    init { orderId?.let { super.setId(it) } }

    companion object { fun builder(): Builder = Builder() }

    data class Builder(
        var orderId: OrderId? = null,
        var orderStatus: OrderStatus? = null,
        var totalAmount: Money? = null,
        var products: List<Product>? = null,
    ) {
        fun orderId(orderId: OrderId?) = apply { this.orderId = orderId }
        fun orderStatus(orderStatus: OrderStatus?) = apply { this.orderStatus = orderStatus }
        fun totalAmount(totalAmount: Money?) = apply { this.totalAmount = totalAmount }
        fun products(products: List<Product>?) = apply { this.products = products }
        fun build() = OrderData(
            orderId, orderStatus, totalAmount, products
        )
    }
}
