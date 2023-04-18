package co.cp.orderly.payment.domain.service.consistency.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.ZonedDateTime

data class OrderEventPayloadDTO(
    @JsonProperty
    val paymentId: String,

    @JsonProperty
    val customerId: String,

    @JsonProperty
    val orderId: String,

    @JsonProperty
    val price: BigDecimal,

    @JsonProperty
    val createdAt: ZonedDateTime,

    @JsonProperty
    val paymentStatus: String,

    @JsonProperty
    val failureMessages: List<String>
)
