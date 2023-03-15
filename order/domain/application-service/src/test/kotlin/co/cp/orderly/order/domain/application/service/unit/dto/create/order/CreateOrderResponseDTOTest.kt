package co.cp.orderly.order.domain.application.service.unit.dto.create.order

import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class CreateOrderResponseDTOTest {
    private val orderResponseDTO = CreateOrderResponseDTO(
        UUID.fromString("400f119c-79dc-4344-946a-761525ab0b38"),
        OrderStatus.PENDING,
        "Order has been successfully created"
    )

    private val orderResponseDTO2nd = CreateOrderResponseDTO(
        UUID.fromString("400f119c-79dc-4344-946a-761525ab0b38"),
        OrderStatus.PENDING,
    )

    @Test
    fun `should construct CreateOrderResponseDTOTest and check its data properties`() {
        Assertions.assertNotNull(orderResponseDTO.orderTrackingId)
        Assertions.assertNotNull(orderResponseDTO.orderStatus)
        Assertions.assertNotNull(orderResponseDTO.message)
        Assertions.assertNull(orderResponseDTO2nd.message)
    }

    @Test
    fun `should check the validity of CreateOrderResponseDTOTest toString() method`() {
        Assertions.assertEquals(
            orderResponseDTO.toString(),
            "CreateOrderResponseDTO(" +
                "orderTrackingId=400f119c-79dc-4344-946a-761525ab0b38, " +
                "orderStatus=PENDING, " +
                "message=Order has been successfully created)"
        )
    }
}
