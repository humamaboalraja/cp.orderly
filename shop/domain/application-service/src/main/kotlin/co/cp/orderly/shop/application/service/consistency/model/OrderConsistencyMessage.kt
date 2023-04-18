package co.cp.orderly.shop.application.service.consistency.model

import ConsistencyState
import co.cp.orderly.domain.vos.OrderApprovalStatus
import java.time.ZonedDateTime
import java.util.UUID

data class OrderConsistencyMessage(
    val id: UUID,
    val lltId: UUID,
    val createdAt: ZonedDateTime,
    val processedAt: ZonedDateTime? = null,
    val type: String,
    val payload: String,
    var consistencyState: ConsistencyState,
    val approvalStatus: OrderApprovalStatus,
    val version: Int? = 0
)
