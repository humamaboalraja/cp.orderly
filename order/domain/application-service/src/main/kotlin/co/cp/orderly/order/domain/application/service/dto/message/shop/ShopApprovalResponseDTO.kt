package co.cp.orderly.order.domain.application.service.dto.message.shop

import co.cp.orderly.domain.vos.OrderApprovalStatus
import java.time.Instant
import javax.validation.constraints.NotNull

data class ShopApprovalResponseDTO(
    @NotNull
    val id: String,
    @NotNull
    val sagaId: String,
    @NotNull
    val orderId: String,
    @NotNull
    val shopId: String,
    @NotNull
    val createdAt: Instant,
    @NotNull
    val orderApprovalStatus: OrderApprovalStatus,
    @NotNull
    val errorMessages: MutableList<String>,

)
