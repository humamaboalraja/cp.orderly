package co.cp.orderly.domain.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

class OrderIdTest {

    @Test
    fun `should construct and get OrderId's UUID value`() {
        val orderId = OrderId(UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(orderId.getValue(), UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
    }

    @Test
    fun `should expect the desired results from OrderId's hashCode(), toString() and equals() methods`() {
        val orderId = OrderId(UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(orderId.toString(), "BaseId(value=36418fe4-efc1-45c1-a013-51be6af1bb0e)")
        assertTrue(orderId.getValue() == UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(orderId.hashCode(), 325197973)
        assertTrue(orderId == orderId)
    }
}
