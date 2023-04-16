package co.cp.orderly.order.domain.application.service.unit.dto.track.order

import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderQueryDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class TrackOrderQueryDTOTest {

    private val trackOrderQueryDTO = TrackOrderQueryDTO(
        UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"),
    )

    @Test
    fun `should construct TrackOrderQueryDTO and check its data properties`() {
        Assertions.assertNotNull(trackOrderQueryDTO.orderTrackingId)
    }

    @Test
    fun `should check the validity of TrackOrderQueryDTO toString() method`() {
        Assertions.assertEquals(
            trackOrderQueryDTO.toString(),
            "TrackOrderQueryDTO(orderTrackingId=36418fe4-efc1-45c1-a013-51be6af1bb0e)"
        )
    }
}
