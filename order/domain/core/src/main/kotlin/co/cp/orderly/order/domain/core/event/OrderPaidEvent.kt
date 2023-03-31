package co.cp.orderly.order.domain.core.event

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.order.domain.core.entity.Order
import java.time.ZonedDateTime

class OrderPaidEvent(
    order: Order,
    createdAt: ZonedDateTime,
    private val orderCanceledDomainEventPublisher: DomainEventPublisher<OrderPaidEvent>? = null
) : OrderEvent(order, createdAt) {
    override fun fire(): Unit = run { orderCanceledDomainEventPublisher?.publish(this) }
}
