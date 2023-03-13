package co.cp.orderly.order.domain.application.service.dto.internal.order

import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.NotNull

data class OrderItemDTO(
    @NotNull
    private val productId: UUID,
    @NotNull
    private val quantity: Int,
    @NotNull
    private val price: BigDecimal,
    @NotNull
    private val subTotal: BigDecimal

)
