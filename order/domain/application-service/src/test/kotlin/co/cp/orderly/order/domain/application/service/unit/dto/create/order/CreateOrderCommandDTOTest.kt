package co.cp.orderly.order.domain.application.service.unit.dto.create.order

import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderAddressDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderItemDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class CreateOrderCommandDTOTest {

    private val orderCommandDTO = CreateOrderCommandDTO(
        UUID.fromString("400f119c-79dc-4344-946a-761525ab0b38"),
        UUID.fromString("2d495872-bcbc-4b62-91c5-1c546397d2a1"),
        BigDecimal(1234),
        listOf(
            OrderItemDTO(
                UUID.fromString("568409d6-0aba-43f4-824e-833a413a6bbd"),
                21345,
                BigDecimal(234),
                BigDecimal(234)
            )
        ),
        OrderAddressDTO(
            "Street Name 12A",
            "2342",
            "Berlin"
        )
    )

    @Test
    fun `should construct CreateOrderCommandDTOTest and check its data properties`() {
        assertNotNull(orderCommandDTO.customerId)
        assertNotNull(orderCommandDTO.shopId)
        assertNotNull(orderCommandDTO.price)
        assertNotNull(orderCommandDTO.items)
        assertNotNull(orderCommandDTO.orderAddress)
    }

    @Test
    fun `should check the validity of CreateOrderCommandDTOTest toString() method`() {
        assertEquals(
            orderCommandDTO.toString(),
            "CreateOrderCommandDTO(" +
                "customerId=400f119c-79dc-4344-946a-761525ab0b38, " +
                "shopId=2d495872-bcbc-4b62-91c5-1c546397d2a1, " +
                "price=1234, " +
                "items=[OrderItemDTO(" +
                "productId=568409d6-0aba-43f4-824e-833a413a6bbd, " +
                "quantity=21345, price=234, subTotal=234)], " +
                "orderAddress=OrderAddressDTO(streetName=Street Name 12A, " +
                "postalCode=2342, city=Berlin))"
        )
    }
}
