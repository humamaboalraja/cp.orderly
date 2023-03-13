package co.cp.orderly.order.domain.application.service.dto.create

import javax.validation.constraints.Max
import javax.validation.constraints.NotNull

data class OrderAddressDTO(
    @NotNull
    private val streetName: String?,
    @NotNull
    @Max(value = 10)
    private val postalCode: String?,
    @NotNull
    @Max(value = 85)
    private val city: String,
)
