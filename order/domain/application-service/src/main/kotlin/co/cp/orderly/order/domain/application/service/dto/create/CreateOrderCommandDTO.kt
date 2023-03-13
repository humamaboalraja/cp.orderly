package co.cp.orderly.order.domain.application.service.dto.create

import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.NotNull

data class CreateOrderCommandDTO(
    @NotNull
    private val customerId: UUID,
    @NotNull
    private val shopId: UUID?,
    @NotNull
    private val price: BigDecimal?,
    @NotNull
    private val items: List<OrderItemDTO>?,
    @NotNull
    private val orderAddress: OrderAddressDTO?
)
