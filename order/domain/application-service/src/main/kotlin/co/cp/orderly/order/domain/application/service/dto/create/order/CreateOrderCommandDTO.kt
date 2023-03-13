package co.cp.orderly.order.domain.application.service.dto.create.order

import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderAddressDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderItemDTO
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
