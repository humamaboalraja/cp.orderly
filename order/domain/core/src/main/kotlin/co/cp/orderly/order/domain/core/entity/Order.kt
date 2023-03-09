package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.AggregateRoot
import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.order.domain.core.vos.StreetAddress
import co.cp.orderly.order.domain.core.vos.TrackingId

class Order private constructor(
    val orderId: OrderId?,
    val customerId: CustomerId?,
    val shopId: ShopId?,
    val deliveryAddress: StreetAddress?,
    val price: Money?,
    val items: List<OrderItem>?,
    var trackingId: TrackingId?,
    var orderStatus: OrderStatus?,
    var failureMessages: MutableList<String>
) : AggregateRoot<OrderId>() {

    init { orderId?.let { super.setId(it) } }
    companion object { fun builder(): Builder = Builder() }

    data class Builder(
        var orderId: OrderId? = null,
        var customerId: CustomerId? = null,
        var shopId: ShopId? = null,
        var deliveryAddress: StreetAddress? = null,
        var price: Money? = null,
        var items: List<OrderItem>? = null,
        var trackingId: TrackingId? = null,
        var orderStatus: OrderStatus? = null,
        var failureMessages: MutableList<String> = mutableListOf()
    ) {
        fun orderId(orderId: OrderId?) = apply { this.orderId = orderId }
        fun customerId(customerId: CustomerId?) = apply { this.customerId = customerId }
        fun shopId(shopId: ShopId?) = apply { this.shopId = shopId }
        fun deliveryAddress(deliveryAddress: StreetAddress) = apply { this.deliveryAddress = deliveryAddress }
        fun price(price: Money?) = apply { this.price = price }
        fun items(items: List<OrderItem>?) = apply { this.items = items }
        fun trackingId(trackingId: TrackingId?) = apply { this.trackingId = trackingId }
        fun orderStatus(orderStatus: OrderStatus?) = apply { this.orderStatus = orderStatus }
        fun failureMessages(failureMessages: MutableList<String>?) = apply {
            failureMessages?.let { this.failureMessages = failureMessages }
        }
        fun build() = Order(
            orderId, customerId, shopId, deliveryAddress, price, items, trackingId, orderStatus, failureMessages
        )
    }

    override fun toString(): String =
        "Order(orderId=$orderId, \n" +
            "customerId=$customerId, \n" +
            "shopId=$shopId, \n" +
            "deliveryAddress=$deliveryAddress, \n" +
            "price=$price, \n" +
            "items=$items, \n" +
            "trackingId=$trackingId, \n" +
            "orderStatus=$orderStatus, \n" +
            "failureMessages=$failureMessages)"
}
