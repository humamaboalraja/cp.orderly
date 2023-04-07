package co.cp.orderly.order.domain.application.service.dto.internal.order

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

data class OrderItemDTO(
    @NotNull
    val productId: UUID,
    @NotNull
    val quantity: Int,
    @NotNull
    val price: BigDecimal,
    @NotNull
    val subTotal: BigDecimal

)
