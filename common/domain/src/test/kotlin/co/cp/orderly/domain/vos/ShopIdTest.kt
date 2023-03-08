package co.cp.orderly.domain.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

class ShopIdTest {

    @Test
    fun `should construct and get ShopId's UUID value`() {
        val shopId = ShopId(UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(shopId.getValue(), UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
    }

    @Test
    fun `should expect the desired results from ShopId's hashCode(), toString() and equals() methods`() {
        val shopId = ShopId(UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(shopId.toString(), "BaseId(value=36418fe4-efc1-45c1-a013-51be6af1bb0e)")
        assertTrue(shopId.getValue() == UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"))
        assertEquals(shopId.hashCode(), 325197973)
        assertTrue(shopId == shopId)
    }
}
