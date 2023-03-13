package co.cp.orderly.order.domain.application.service.dto.message

import co.cp.orderly.domain.vos.OrderApprovalStatus
import java.time.Instant
import javax.validation.constraints.NotNull

data class ShopApprovalResponseDTO(
    @NotNull
    private val id: String,
    @NotNull
    private val sagaId: String,
    @NotNull
    private val orderId: String,
    @NotNull
    private val shopId: String,
    @NotNull
    private val createdAt: Instant,
    @NotNull
    private val orderApprovalStatus: OrderApprovalStatus,
    @NotNull
    private val errorMessages: MutableList<String>,

)
