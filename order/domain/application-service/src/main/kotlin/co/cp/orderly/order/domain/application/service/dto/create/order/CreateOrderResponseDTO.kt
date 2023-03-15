package co.cp.orderly.order.domain.application.service.dto.create.order

import co.cp.orderly.domain.vos.OrderStatus
import java.util.UUID
import javax.validation.constraints.NotNull

data class CreateOrderResponseDTO(
    @NotNull
    val orderTrackingId: UUID?,
    @NotNull
    val orderStatus: OrderStatus?,
    val message: String? = null,
)
