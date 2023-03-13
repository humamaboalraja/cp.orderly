package co.cp.orderly.order.domain.application.service.dto.message.payment

import co.cp.orderly.domain.vos.PaymentStatus
import java.time.Instant
import javax.validation.constraints.NotNull

data class PaymentResponseDTO(
    @NotNull
    val id: String,
    @NotNull
    val sagaId: String,
    @NotNull
    val orderId: String,
    @NotNull
    val paymentId: String,
    @NotNull
    val customerId: String,
    @NotNull
    val createdAt: Instant,
    @NotNull
    val paymentStatus: PaymentStatus,
    @NotNull
    val errorMessages: MutableList<String>,

)