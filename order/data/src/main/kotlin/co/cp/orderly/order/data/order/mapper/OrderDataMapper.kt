package co.cp.orderly.order.data.order.mapper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.order.data.order.entity.OrderAddressEntity
import co.cp.orderly.order.data.order.entity.OrderEntity
import co.cp.orderly.order.data.order.entity.OrderItemEntity
import co.cp.orderly.order.domain.core.constants.OrderConstants.ERROR_MESSAGES_DELIMITER
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.OrderItem
import co.cp.orderly.order.domain.core.entity.Product
import co.cp.orderly.order.domain.core.vos.OrderItemId
import co.cp.orderly.order.domain.core.vos.StreetAddress
import co.cp.orderly.order.domain.core.vos.TrackingId
import org.springframework.stereotype.Component

@Component
class OrderDataMapper {
    fun orderToOrderEntity(order: Order) =
        OrderEntity(
            order.getId()?.getValue(),
            order.customerId?.getValue(),
            order.shopId?.getValue(),
            order.trackingId?.getValue(),
            items = orderItemsToOrderItemEntities(order.items),
            address = deliveryAddressToAddressEntity(order.deliveryAddress),
            price = order.price?.getAmount(),
            orderStatus = order.orderStatus,
            errorMessages = order.errorMessages?.joinToString { ERROR_MESSAGES_DELIMITER }
        ).also {
            it.address?.order = it
            it.items?.forEach { orderItemEntity -> orderItemEntity.order = it }
        }

    fun orderEntityToOrder(orderEntity: OrderEntity) = Order.builder()
        .orderId(OrderId(orderEntity.orderId!!))
        .customerId(CustomerId(orderEntity.customerId!!))
        .shopId(ShopId(orderEntity.shopId!!))
        .orderStatus(orderEntity.orderStatus)
        .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.address))
        .price(Money(orderEntity.price!!))
        .items(orderItemEntitiesToOrderItems(orderEntity.items!!))
        .trackingId(TrackingId(orderEntity.trackingId!!))
        .errorMessages(
            when {
                orderEntity.errorMessages?.isEmpty() == true -> mutableListOf()
                else -> orderEntity.errorMessages?.split(ERROR_MESSAGES_DELIMITER)?.toMutableList()
            }
        )
        .build()

    private fun orderItemEntitiesToOrderItems(items: List<OrderItemEntity>) =
        items.map {
            OrderItem.builder()
                .orderItemId(OrderItemId(it.id!!))
                .product(Product(ProductId(it.productId!!)))
                .quantity(it.quantity)
                .price(Money(it.price!!))
                .subTotal(Money(it.subTotal!!)).build()
        }.toList()

    private fun addressEntityToDeliveryAddress(address: OrderAddressEntity?) =
        StreetAddress(address?.id!!, address.street!!, address.postalCode!!, address.city!!)

    private fun orderItemsToOrderItemEntities(items: List<OrderItem>?): List<OrderItemEntity>? =
        items?.map {
            OrderItemEntity(
                it.getId()?.getValue(),
                productId = it.product?.getId()?.getValue(),
                quantity = it.quantity,
                price = it.price?.getAmount(),
                subTotal = it.subTotal?.getAmount(),
            )
        }?.toList()

    private fun deliveryAddressToAddressEntity(deliveryAddress: StreetAddress?) = OrderAddressEntity(
        deliveryAddress?.id,
        street = deliveryAddress?.street, postalCode = deliveryAddress?.postalCode, city = deliveryAddress?.city
    )
}
