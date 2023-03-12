package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.AggregateRoot
import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import co.cp.orderly.order.domain.core.vos.OrderItemId
import co.cp.orderly.order.domain.core.vos.StreetAddress
import co.cp.orderly.order.domain.core.vos.TrackingId
import java.math.BigDecimal
import java.util.UUID

class Order private constructor(
    val orderId: OrderId?,
    val customerId: CustomerId?,
    val shopId: ShopId?,
    val deliveryAddress: StreetAddress?,
    val price: Money?,
    val items: List<OrderItem>?,
    var trackingId: TrackingId?,
    var orderStatus: OrderStatus?,
    var errorMessages: MutableList<String>?
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
        var errorMessages: MutableList<String>? = null
    ) {
        fun orderId(orderId: OrderId?) = apply { this.orderId = orderId }
        fun customerId(customerId: CustomerId?) = apply { this.customerId = customerId }
        fun shopId(shopId: ShopId?) = apply { this.shopId = shopId }
        fun deliveryAddress(deliveryAddress: StreetAddress) = apply { this.deliveryAddress = deliveryAddress }
        fun price(price: Money?) = apply { this.price = price }
        fun items(items: List<OrderItem>?) = apply { this.items = items }
        fun trackingId(trackingId: TrackingId?) = apply { this.trackingId = trackingId }
        fun orderStatus(orderStatus: OrderStatus?) = apply { this.orderStatus = orderStatus }
        fun errorMessages(errorMessages: MutableList<String>?) = apply { this.errorMessages = errorMessages }
        fun build() = Order(
            orderId, customerId, shopId, deliveryAddress, price, items, trackingId, orderStatus, errorMessages
        )
    }

    fun startNewtOrder() {
        setId(OrderId(UUID.randomUUID()))
        trackingId = TrackingId(UUID.randomUUID())
        orderStatus = OrderStatus.PENDING
        initializeOrderItems()
    }

    private fun changeOrderState(
        operationCondition: () -> Boolean,
        exceptionMessage: String,
        operation: () -> Unit
    ) = when {
        operationCondition() -> throw OrderDomainException(exceptionMessage)
        else -> Unit
    }.also { operation() }

    fun pay() = changeOrderState(
        operationCondition = { orderStatus != OrderStatus.PENDING },
        exceptionMessage = "Order is not in valid state for a pay operation",
        operation = { orderStatus = OrderStatus.PAID }
    )

    fun approve() = changeOrderState(
        operationCondition = { orderStatus != OrderStatus.PAID },
        exceptionMessage = "Order is not in valid state for an approve operation",
        operation = { orderStatus = OrderStatus.APPROVED }
    )

    fun initCancel(errorMessages: MutableList<String>?) = changeOrderState(
        operationCondition = { orderStatus != OrderStatus.PAID },
        exceptionMessage = "Order is not in a valid state for an initCancel operation",
        operation = {
            orderStatus = OrderStatus.CANCELLING
            updateErrorMessages(errorMessages)
        }
    )

    fun cancel(errorMessages: MutableList<String>?) = changeOrderState(
        operationCondition = { !(orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING) },
        exceptionMessage = "Order is not in a valid state for a cancel operation",
        operation = {
            orderStatus = OrderStatus.CANCELED
            updateErrorMessages(errorMessages)
        }
    )

    private fun updateErrorMessages(errorMessages: MutableList<String>?) {
        when {
            this.errorMessages != null && errorMessages != null ->
                this.errorMessages?.addAll(errorMessages.filterNot { it.isEmpty() }.toMutableList())
            this.errorMessages == null -> this.errorMessages = errorMessages
        }
    }

    fun validateOrder() {
        validateInitialOrder()
        validateTotalPrice()
        validateItemsPrice()
    }

    private fun validateItemsPrice() = items?.let { list ->
        list.map { orderItem ->
            validateItemPrice(orderItem)
            orderItem.subTotal
        }.fold(Money.ZERO_VALUE) { acc, money ->
            if (money == null) return Money(BigDecimal(0))
            money.addMoney(acc)
        }.also {
            when {
                price == null -> Money(BigDecimal(0))
                price.getAmount().toInt() != it.getAmount().toInt() -> throw OrderDomainException(
                    "Total Price: ${price.getAmount()} doesn't match the order items total price ${it.getAmount()}"
                )
            }
        }
    }

    private fun validateItemPrice(orderItem: OrderItem) {
        when {
            orderItem.price == null -> return
            orderItem.product == null -> return
            !orderItem.isPriceValid() ->
                throw OrderDomainException(
                    "Order item price: ${orderItem.price.getAmount()} is not valid for product: ${orderItem.product.getId()?.getValue()}"
                )
        }
    }

    private fun validateTotalPrice() = when {
        price == null || !price.isAmountGreaterThanZero() ->
            throw OrderDomainException("The total price must be greater than 0")
        else -> Unit
    }

    private fun validateInitialOrder() = when {
        orderStatus != null || getId() != null ->
            throw OrderDomainException("Order is not in a correct state for initialization")
        else -> Unit
    }

    private fun initializeOrderItems() {
        var itemId: Long = 1
        items?.forEach {
            it.initializeOrderItem(super.getId(), OrderItemId(itemId++))
        }
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
            "errorMessages=$errorMessages)"
}
