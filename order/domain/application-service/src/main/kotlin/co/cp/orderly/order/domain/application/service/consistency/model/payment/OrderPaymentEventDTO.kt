package co.cp.orderly.order.domain.application.service.consistency.model.payment

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.ZonedDateTime

data class OrderPaymentEventDTO(

    @JsonProperty
    var orderId: String,

    @JsonProperty
    val customerId: String,

    @JsonProperty
    val price: BigDecimal,

    @JsonProperty
    val createdAt: ZonedDateTime,

    @JsonProperty
    val paymentOrderStatus: String

)
