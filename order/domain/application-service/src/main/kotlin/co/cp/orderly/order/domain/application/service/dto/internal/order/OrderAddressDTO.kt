package co.cp.orderly.order.domain.application.service.dto.internal.order

import javax.validation.constraints.Max
import javax.validation.constraints.NotNull

data class OrderAddressDTO(
    @NotNull
    val streetName: String,
    @NotNull
    @Max(value = 10)
    val postalCode: String,
    @NotNull
    @Max(value = 85)
    val city: String,
)
