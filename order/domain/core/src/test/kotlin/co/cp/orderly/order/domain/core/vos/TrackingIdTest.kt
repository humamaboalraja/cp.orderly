package co.cp.orderly.order.domain.core.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class TrackingIdTest {
    @Test
    fun `should call and construct Tracking id and it parent`() {
        val trackingId = TrackingId(UUID.fromString("478576d1-f6bc-406c-8926-9abdd80a30e5"))
        assertEquals(trackingId.getValue(), UUID.fromString("478576d1-f6bc-406c-8926-9abdd80a30e5"))
        assertEquals(trackingId.toString(), "BaseId(value=478576d1-f6bc-406c-8926-9abdd80a30e5)")
    }
}
