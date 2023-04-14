package co.cp.orderly.order.domain.application.service.consistency.model.approval

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.ZonedDateTime

data class OrderApprovalEventDTO(
    @JsonProperty
    val orderId: String,

    @JsonProperty
    val shopId: String,

    @JsonProperty
    val price: BigDecimal,

    @JsonProperty
    val createdAt: ZonedDateTime,

    @JsonProperty
    val products: List<OrderApprovalEventProduct>,

    @JsonProperty
    val shopOrderStatus: String
)
