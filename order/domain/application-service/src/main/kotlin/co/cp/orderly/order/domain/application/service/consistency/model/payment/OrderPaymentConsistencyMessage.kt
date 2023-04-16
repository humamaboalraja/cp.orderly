package co.cp.orderly.order.domain.application.service.consistency.model.payment

import ConsistencyState
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import java.time.ZonedDateTime
import java.util.UUID

data class OrderPaymentConsistencyMessage(
    val id: UUID,
    val lltId: UUID,
    val createdAt: ZonedDateTime,
    var processedAt: ZonedDateTime? = null,
    val type: String,
    val payload: String,
    var lltStatus: LongRunningTransactionState,
    var orderStatus: OrderStatus,
    var consistencyState: ConsistencyState,
    val version: Int? = null

)
