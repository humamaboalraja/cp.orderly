package co.cp.orderly.order.domain.application.service.dto.track.order

import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.NotNull

data class TrackOrderQueryDTO(
    @NotNull
    private val orderTrackingId: UUID,
    @NotNull
    private val shopId: UUID?,
    @NotNull
    private val price: BigDecimal?,

)
