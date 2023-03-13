package co.cp.orderly.order.domain.application.service.dto.create

import co.cp.orderly.domain.vos.OrderStatus
import java.util.UUID
import javax.validation.constraints.NotNull

// @AllArgsConstructor
// @Builder
// @Getter
data class CreateOrderResponseDTO(
    @NotNull
    private val orderTrackingId: UUID?,
    @NotNull
    private val orderStatus: OrderStatus?,
    @NotNull
    private val message: String?,
)
