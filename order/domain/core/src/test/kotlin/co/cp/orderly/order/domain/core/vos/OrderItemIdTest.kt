package co.cp.orderly.order.domain.core.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OrderItemIdTest {
    @Test
    fun `should call and construct OrderItemId and its parent`() {
        val orderItemId = OrderItemId(3455421343243523)
        assertEquals(orderItemId.getValue(), 3455421343243523)
        assertEquals(orderItemId.toString(), "BaseId(value=3455421343243523)")
    }
}
