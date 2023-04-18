package co.cp.orderly.shop.application.service.consistency.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

class OrderEventPayloadDTO(
    @JsonProperty
    val orderId: String,

    @JsonProperty
    val shopId: String,

    @JsonProperty
    val createdAt: ZonedDateTime,

    @JsonProperty
    val orderApprovalStatus: String,

    @JsonProperty
    val errorMessages: List<String>
)
