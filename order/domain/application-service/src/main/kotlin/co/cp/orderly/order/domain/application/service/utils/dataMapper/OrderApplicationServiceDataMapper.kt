package co.cp.orderly.order.domain.application.service.utils.dataMapper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderAddressDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderItemDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderResponseDTO
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.OrderItem
import co.cp.orderly.order.domain.core.entity.Product
import co.cp.orderly.order.domain.core.entity.Shop
import co.cp.orderly.order.domain.core.vos.StreetAddress
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderApplicationServiceDataMapper {
    fun createOrderCommandToShop(createOrderCommandDTO: CreateOrderCommandDTO): Shop =
        Shop.builder()
            .shopId(ShopId(createOrderCommandDTO.shopId))
            .products(
                createOrderCommandDTO.items.map {
                    Product(ProductId(it.productId), price = it.price)
                }
            )
            .build()

    fun createOrderCommandToOrder(createOrderCommandDTO: CreateOrderCommandDTO): Order =
        Order.builder()
            .customerId(CustomerId(createOrderCommandDTO.customerId))
            .shopId(ShopId(createOrderCommandDTO.shopId))
            .items(orderItemsToOrderItemEntities(createOrderCommandDTO.items))
            .price(Money(createOrderCommandDTO.price))
            .deliveryAddress(orderAddressToStreetAddress(createOrderCommandDTO.orderAddress))
            .build()

    fun orderToCreateOrderResponse(order: Order, message: String): CreateOrderResponseDTO =
        CreateOrderResponseDTO(order.getId()?.getValue(), order.orderStatus, message)

    private fun orderItemsToOrderItemEntities(items: List<OrderItemDTO>): List<OrderItem> =
        items.map { orderItemDTO ->
            OrderItem.builder()
                .product(Product(ProductId(orderItemDTO.productId), orderItemDTO.price))
                .price(Money(orderItemDTO.price))
                .quantity(orderItemDTO.quantity)
                .subTotal(Money(orderItemDTO.subTotal))
                .build()
        }.toList()

    private fun orderAddressToStreetAddress(orderAddressDTO: OrderAddressDTO): StreetAddress =
        StreetAddress(UUID.randomUUID(), orderAddressDTO.streetName, orderAddressDTO.postalCode, orderAddressDTO.city)

    fun orderToTrackOrderResponse(order: Order): TrackOrderResponseDTO =
        TrackOrderResponseDTO(order.trackingId!!.getValue(), order.orderStatus!!, order.errorMessages)
}
