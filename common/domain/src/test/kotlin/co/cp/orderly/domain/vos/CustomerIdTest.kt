package co.cp.orderly.domain.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

class CustomerIdTest {

    @Test
    fun `should construct and get CustomerId's UUID value`() {
        val customerId = CustomerId(UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(customerId.getValue(), UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
    }

    @Test
    fun `should expect the desired results from CustomerId's hashCode(), toString() and equals() methods`() {
        val customerId = CustomerId(UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(customerId.toString(), "BaseId(value=36418fe4-efc1-45c1-a013-51be6af1bb0e)")
        assertTrue(customerId.getValue() == UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(customerId.hashCode(), 325197973)
        assertTrue(customerId == customerId)
    }
}
