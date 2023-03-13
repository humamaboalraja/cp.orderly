package co.cp.orderly.order.domain.application.service.unit.dto.internal.order

import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderAddressDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrderAddressDTOTest {

    private val orderAddressDTO = OrderAddressDTO(
        "Street Name 12A",
        "2342",
        "Berlin"
    )

    @Test
    fun `should construct OrderAddressDTO and check its data properties`() {
        Assertions.assertNotNull(orderAddressDTO.streetName)
        Assertions.assertNotNull(orderAddressDTO.postalCode)
        Assertions.assertNotNull(orderAddressDTO.city)
    }

    @Test
    fun `should check the validity of OrderAddressDTO toString() method`() {
        Assertions.assertEquals(
            orderAddressDTO.toString(),
            "OrderAddressDTO(streetName=Street Name 12A, " +
                "postalCode=2342, city=Berlin)"
        )
    }
}
