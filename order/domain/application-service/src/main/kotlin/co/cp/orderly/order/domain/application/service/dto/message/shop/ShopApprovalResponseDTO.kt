package co.cp.orderly.order.domain.application.service.dto.message.shop

import co.cp.orderly.domain.vos.OrderApprovalStatus
import jakarta.validation.constraints.NotNull
import java.time.Instant

data class ShopApprovalResponseDTO(
    @NotNull
    val id: String,
    @NotNull
    val lltId: String,
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
