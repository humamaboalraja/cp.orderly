package co.cp.orderly.order.domain.application.service.consistency.model.approval

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class OrderApprovalEventProduct(

    @JsonProperty
    var id: UUID,

    @JsonProperty
    val quantity: Int,
)
