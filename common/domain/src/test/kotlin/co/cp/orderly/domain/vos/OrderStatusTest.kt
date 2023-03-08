package co.cp.orderly.domain.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OrderStatusTest {
    private val orderStatus = OrderStatus.values()

    @Test
    fun `should call OrderStatus and inspect its values`() {
        assertEquals(orderStatus[0], OrderStatus.PENDING)
        assertEquals(orderStatus[1], OrderStatus.PAID)
        assertEquals(orderStatus[2], OrderStatus.APPROVED)
        assertEquals(orderStatus[3], OrderStatus.CANCELLING)
        assertEquals(orderStatus[4], OrderStatus.CANCELED)
    }
}
