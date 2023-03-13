package co.cp.orderly.order.domain.application.service.unit.dto.internal.order

import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderItemDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class OrderItemDTOTest {
    private val orderItemDTO = OrderItemDTO(
        UUID.fromString("568409d6-0aba-43f4-824e-833a413a6bbd"),
        21345,
        BigDecimal(234),
        BigDecimal(234)
    )

    @Test
    fun `should construct OrderItemDTO and check its data properties`() {
        Assertions.assertNotNull(orderItemDTO.productId)
        Assertions.assertNotNull(orderItemDTO.quantity)
        Assertions.assertNotNull(orderItemDTO.price)
        Assertions.assertNotNull(orderItemDTO.subTotal)
    }

    @Test
    fun `should check the validity of OrderItemDTO toString() method`() {
        Assertions.assertEquals(
            orderItemDTO.toString(),
            "OrderItemDTO(productId=568409d6-0aba-43f4-824e-833a413a6bbd, " +
                "quantity=21345, price=234, subTotal=234)"
        )
    }
}
