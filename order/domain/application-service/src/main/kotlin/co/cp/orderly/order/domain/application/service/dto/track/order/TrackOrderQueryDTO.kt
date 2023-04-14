package co.cp.orderly.order.domain.application.service.dto.track.order

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class TrackOrderQueryDTO(
    @NotNull
    val orderTrackingId: UUID,

)
