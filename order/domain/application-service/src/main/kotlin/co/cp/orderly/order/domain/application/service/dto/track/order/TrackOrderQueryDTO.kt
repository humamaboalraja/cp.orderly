package co.cp.orderly.order.domain.application.service.dto.track.order

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

data class TrackOrderQueryDTO(
    @NotNull
    val orderTrackingId: UUID,
    @NotNull
    val shopId: UUID,
    @NotNull
    val price: BigDecimal,

)
