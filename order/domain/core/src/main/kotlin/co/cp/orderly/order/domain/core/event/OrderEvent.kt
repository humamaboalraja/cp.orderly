package co.cp.orderly.order.domain.core.event

import co.cp.orderly.domain.event.DomainEvent
import co.cp.orderly.order.domain.core.entity.Order
import java.time.ZonedDateTime

open abstract class OrderEvent(
    val order: Order,
    val createdAt: ZonedDateTime
) : DomainEvent<Order>
