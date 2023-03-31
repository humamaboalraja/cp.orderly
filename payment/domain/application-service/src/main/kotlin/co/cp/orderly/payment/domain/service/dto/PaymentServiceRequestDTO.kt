package co.cp.orderly.payment.domain.service.dto

import co.cp.orderly.domain.vos.PaymentOrderStatus
import java.math.BigDecimal
import java.time.Instant

data class PaymentServiceRequestDTO(
    var id: String,
    var sagaId: String,
    var orderId: String,
    var customerId: String,
    var price: BigDecimal,
    var paymentOrderStatus: PaymentOrderStatus,
    var createdAt: Instant
)
