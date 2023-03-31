package co.cp.orderly.order.domain.core.service

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.Product
import co.cp.orderly.order.domain.core.entity.Shop
import co.cp.orderly.order.domain.core.event.OrderCancelledEvent
import co.cp.orderly.order.domain.core.event.OrderCreatedEvent
import co.cp.orderly.order.domain.core.event.OrderPaidEvent
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.logging.Logger

open class OrderDomainServiceImpl : IOrderDomainService {

    companion object { private val logger = Logger.getLogger(OrderDomainServiceImpl::class.java.name) }
    private val utcZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"))

    override fun validateAndStartOrder(
        order: Order,
        shop: Shop,
        orderCreatedDomainEventPublisher: DomainEventPublisher<OrderCreatedEvent>?
    ): OrderCreatedEvent {
        validateShop(shop)
        setOrderProductData(order, shop)
        order.validateOrder()
        order.startNewtOrder()

        logger.info("Order #${order.getId()!!.getValue()} is initiated")

        return OrderCreatedEvent(order, utcZonedDateTime, orderCreatedDomainEventPublisher)
    }

    override fun payOrder(
        order: Order,
        orderCanceledDomainEventPublisher: DomainEventPublisher<OrderPaidEvent>?
    ): OrderPaidEvent {
        order.pay()
        logger.info { "Order #${order.getId()!!.getValue()} is paid" }
        return OrderPaidEvent(order, utcZonedDateTime, orderCanceledDomainEventPublisher)
    }

    override fun approveOrder(order: Order) {
        order.approve()
        logger.info { "Order with id ${order.getId()!!.getValue()} is approved" }
    }

    override fun cancelOrderPayment(
        order: Order,
        errorMessages: MutableList<String>,
        orderCanceledDomainEventPublisher: DomainEventPublisher<OrderCancelledEvent>?
    ): OrderCancelledEvent {
        order.initCancel(errorMessages)
        logger.info { "Order payment for Order #${order.getId()!!.getValue()} is cancelling " }
        return OrderCancelledEvent(order, utcZonedDateTime, orderCanceledDomainEventPublisher)
    }

    override fun cancelOrder(order: Order, errorMessages: MutableList<String>) {
        order.cancel(errorMessages)
        logger.info { "Order #${order.getId()!!.getValue()} is cancelled" }
    }

    private fun validateShop(shop: Shop) {
        when {
            shop.active == false ->
                throw OrderDomainException("Shop #${shop.getId()!!.getValue()} is currently inactive")
        }
    }

    private fun setOrderProductData(order: Order, shop: Shop) {
        val shopProductsMap = mutableMapOf<Product, Int>()

        var shopProductsMapValueIndex = 0
        order.items!!.forEach { element ->
            shopProductsMap[element.product!!] = shopProductsMapValueIndex
            shopProductsMapValueIndex++
        }
        shop.products!!.forEach { product ->
            when {
                shopProductsMap.containsKey(product) ->
                    order.items[shopProductsMap[product]!!].product!!.updateWithVerifiedNameAndPrice(
                        product.name!!, product.price!!
                    )
            }
        }
    }
}
