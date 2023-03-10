package co.cp.orderly.domain.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

class ProductIdTest {

    @Test
    fun `should construct and get ProductId's UUID value`() {
        val productId = ProductId(UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(productId.getValue(), UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
    }

    @Test
    fun `should expect the desired results from ProductId's hashCode(), toString() and equals() methods`() {
        val productId = ProductId(UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(productId.toString(), "BaseId(value=36418fe4-efc1-45c1-a013-51be6af1bb0e)")
        assertTrue(productId.getValue() == UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(productId.hashCode(), 325197973)
        assertTrue(productId == productId)
    }
}
