package co.cp.orderly.order.domain.core.event

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.order.domain.core.entity.Order
import java.time.ZonedDateTime

class OrderCreatedEvent(
    order: Order,
    createdAt: ZonedDateTime,
    private val orderCreatedDomainEventPublisher: DomainEventPublisher<OrderCreatedEvent>? = null
) : OrderEvent(order, createdAt)
