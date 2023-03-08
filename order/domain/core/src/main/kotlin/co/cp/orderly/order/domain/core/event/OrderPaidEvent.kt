package co.cp.orderly.order.domain.core.event

import co.cp.orderly.order.domain.core.entity.Order
import java.time.ZonedDateTime

class OrderPaidEvent(order: Order, createdAt: ZonedDateTime) : OrderEvent(order, createdAt)
