package co.cp.orderly.order.domain.application.service.dto.track.order

import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.NotNull

data class TrackOrderQueryDTO(
    @NotNull
    val orderTrackingId: UUID,
    @NotNull
    val shopId: UUID?,
    @NotNull
    val price: BigDecimal?,

)
