package co.cp.orderly.order.domain.application.service.unit.dto.track.order

import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderQueryDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class TrackOrderQueryDTOTest {

    private val trackOrderQueryDTO = TrackOrderQueryDTO(
        UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e"),
        UUID.fromString("2d495872-bcbc-4b62-91c5-1c546397d2a1"),
        BigDecimal(2345)
    )

    @Test
    fun `should construct TrackOrderQueryDTO and check its data properties`() {
        Assertions.assertNotNull(trackOrderQueryDTO.orderTrackingId)
        Assertions.assertNotNull(trackOrderQueryDTO.shopId)
        Assertions.assertNotNull(trackOrderQueryDTO.price)
    }

    @Test
    fun `should check the validity of TrackOrderQueryDTO toString() method`() {
        Assertions.assertEquals(
            trackOrderQueryDTO.toString(),
            "TrackOrderQueryDTO(orderTrackingId=36418fe4-efc1-45c1-a013-51be6af1bb0e, " +
                "shopId=2d495872-bcbc-4b62-91c5-1c546397d2a1, " +
                "price=2345)"
        )
    }
}
