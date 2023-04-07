package co.cp.orderly.order.domain.application.service.dto.track.order

import co.cp.orderly.domain.vos.OrderStatus
import java.util.UUID
import javax.validation.constraints.NotNull

data class TrackOrderResponseDTO(
    @NotNull
    val orderTrackingId: UUID,
    @NotNull
    val orderStatus: OrderStatus,
    val errorMessages: MutableList<String>? = null,

)
