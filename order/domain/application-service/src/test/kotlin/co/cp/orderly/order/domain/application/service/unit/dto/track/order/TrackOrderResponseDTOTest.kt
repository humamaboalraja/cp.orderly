package co.cp.orderly.order.domain.application.service.unit.dto.track.order

import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderResponseDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class TrackOrderResponseDTOTest {
    private val trackOrderResponseDTO = TrackOrderResponseDTO(
        UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"),
        OrderStatus.APPROVED,
        mutableListOf("Succeeded", "failed", "corrupted")
    )

    private val trackOrderResponseDTO2nd = TrackOrderResponseDTO(
        UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"),
        OrderStatus.APPROVED,
    )

    @Test
    fun `should construct TrackOrderResponseDTO and check its data properties`() {
        assertNotNull(trackOrderResponseDTO.orderTrackingId)
        assertNotNull(trackOrderResponseDTO.orderStatus)
        assertNotNull(trackOrderResponseDTO.errorMessages)
        assertNull(trackOrderResponseDTO2nd.errorMessages)
    }

    @Test
    fun `should check the validity of TrackOrderResponseDTO toString() method`() {
        assertEquals(
            trackOrderResponseDTO.toString(),
            "TrackOrderResponseDTO(orderTrackingId=36418fe4-efc1-45c1-a013-51be6af1bb0e, " +
                "orderStatus=APPROVED, " +
                "errorMessages=[Succeeded, failed, corrupted])"
        )
    }
}
