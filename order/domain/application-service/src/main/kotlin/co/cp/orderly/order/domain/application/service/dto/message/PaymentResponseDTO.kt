package co.cp.orderly.order.domain.application.service.dto.message

import co.cp.orderly.domain.vos.PaymentStatus
import java.time.Instant
import javax.validation.constraints.NotNull

data class PaymentResponseDTO(
    @NotNull
    private val id: String,
    @NotNull
    private val sagaId: String,
    @NotNull
    private val orderId: String,
    @NotNull
    private val paymentId: String,
    @NotNull
    private val customerId: String,
    @NotNull
    private val createdAt: Instant,
    @NotNull
    private val paymentStatus: PaymentStatus,
    @NotNull
    private val errorMessages: MutableList<String>,

)
