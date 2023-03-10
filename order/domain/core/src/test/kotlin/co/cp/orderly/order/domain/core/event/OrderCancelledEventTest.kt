package co.cp.orderly.order.domain.core.event

import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.order.domain.core.entity.Order
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.util.UUID

class OrderCancelledEventTest {
    @Test
    fun `should call and construct OrderCancelledEvent and inherit its parent's properties`() {
        val createdAt = ZonedDateTime.now()
        val orderEvent = OrderCancelledEvent(
            Order.builder().orderId(OrderId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db"))).build(),
            createdAt
        )
        Assertions.assertEquals(orderEvent.order.orderId, OrderId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")))
        Assertions.assertEquals(orderEvent.createdAt, createdAt)
    }
}
