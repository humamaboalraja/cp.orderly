package co.cp.orderly.order.domain.application.service.dto.create.order

import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderAddressDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderItemDTO
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

data class CreateOrderCommandDTO(
    @NotNull
    val customerId: UUID,
    @NotNull
    val shopId: UUID,
    @NotNull
    val price: BigDecimal,
    @NotNull
    val items: List<OrderItemDTO>,
    @NotNull
    val orderAddress: OrderAddressDTO
)
