package co.cp.orderly.order.domain.core.event

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.order.domain.core.entity.Order
import java.time.ZonedDateTime

class OrderCancelledEvent(
    order: Order,
    createdAt: ZonedDateTime,
    private val orderCanceledDomainEventPublisher: DomainEventPublisher<OrderCancelledEvent>? = null
) : OrderEvent(order, createdAt)
