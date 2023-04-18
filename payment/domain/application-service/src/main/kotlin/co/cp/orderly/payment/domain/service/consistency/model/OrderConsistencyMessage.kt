package co.cp.orderly.payment.domain.service.consistency.model

import ConsistencyState
import co.cp.orderly.domain.vos.PaymentStatus
import java.time.ZonedDateTime
import java.util.UUID

data class OrderConsistencyMessage(
    val id: UUID,
    val lltId: UUID,
    val createdAt: ZonedDateTime,
    val processedAt: ZonedDateTime? = null,
    val type: String,
    val payload: String,
    val paymentStatus: PaymentStatus,
    var consistencyStatus: ConsistencyState,
    val version: Int = 0
)
