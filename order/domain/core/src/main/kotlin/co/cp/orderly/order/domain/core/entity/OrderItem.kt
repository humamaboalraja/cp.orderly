package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.order.domain.core.vos.OrderItemId

class OrderItem private constructor(
    val orderItemId: OrderItemId?,
    private var orderId: OrderId?,
    val product: Product?,
    private val quantity: Int?,
    val price: Money?,
    val subTotal: Money?
) : BaseEntity<OrderItemId>() {

    init { orderItemId?.let { super.setId(it) } }

    companion object { fun builder(): Builder = Builder() }

    data class Builder(
        var orderItemId: OrderItemId? = null,
        var orderId: OrderId? = null,
        var product: Product? = null,
        var quantity: Int? = null,
        var price: Money? = null,
        var subTotal: Money? = null
    ) {
        fun orderItemId(orderItemId: OrderItemId?) = apply { this.orderItemId = orderItemId }
        fun orderId(orderId: OrderId?) = apply { this.orderId = orderId }
        fun product(product: Product?) = apply { this.product = product }
        fun quantity(quantity: Int?) = apply { this.quantity = quantity }
        fun price(price: Money?) = apply { this.price = price }
        fun subTotal(subTotal: Money?) = apply { this.subTotal = subTotal }
        fun build() = OrderItem(
            orderItemId, orderId, product, quantity, price, subTotal
        )
    }

    override fun toString(): String =
        "OrderItem(orderItemId=$orderItemId, " +
            "orderId=$orderId, " +
            "product=$product, " +
            "quantity=$quantity, " +
            "price=$price, " +
            "subTotal=$subTotal)"

    fun initializeOrderItem(orderId: OrderId?, orderItemId: OrderItemId) {
        this.orderId = orderId
        super.setId(orderItemId)
    }

    // TODO - Document getAmountWithDecimals() and its constraints
    fun isPriceValid(): Boolean =
        when {
            product == null -> false
            quantity == null -> false
            price == null -> false
            subTotal == null -> false
            else -> price.isAmountGreaterThanZero() &&
                price == product.price &&
                price.multiplyMoney(quantity) == subTotal.getAmountWithDecimals()
        }
}
