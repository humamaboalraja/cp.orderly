package co.cp.orderly.order.domain.core.service

import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.Shop
import co.cp.orderly.order.domain.core.event.OrderCancelledEvent
import co.cp.orderly.order.domain.core.event.OrderCreatedEvent
import co.cp.orderly.order.domain.core.event.OrderPaidEvent

interface IOrderDomainService {

    fun validateAndStartOrder(order: Order, shop: Shop): OrderCreatedEvent

    fun payOrder(order: Order): OrderPaidEvent

    fun approveOrder(order: Order)

    fun cancelOrderPayment(order: Order, errorMessages: MutableList<String>): OrderCancelledEvent

    fun cancelOrder(order: Order, errorMessages: MutableList<String>)
}
