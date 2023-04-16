package co.cp.orderly.order.domain.application.service.utils.dataMapper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.PaymentOrderStatus
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.domain.vos.ShopOrderStatus
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalEventDTO
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalEventProduct
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentEventDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderAddressDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderItemDTO
import co.cp.orderly.order.domain.application.service.dto.message.customer.CustomerModelDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderResponseDTO
import co.cp.orderly.order.domain.core.entity.Customer
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.OrderItem
import co.cp.orderly.order.domain.core.entity.Product
import co.cp.orderly.order.domain.core.entity.Shop
import co.cp.orderly.order.domain.core.event.OrderCancelledEvent
import co.cp.orderly.order.domain.core.event.OrderCreatedEvent
import co.cp.orderly.order.domain.core.event.OrderPaidEvent
import co.cp.orderly.order.domain.core.vos.StreetAddress
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderApplicationServiceDataMapper {
    fun createOrderCommandToShop(createOrderCommandDTO: CreateOrderCommandDTO): Shop =
        Shop.builder()
            .shopId(ShopId(createOrderCommandDTO.shopId))
            .products(createOrderCommandDTO.items.map { Product(ProductId(it.productId), price = it.price) })
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
        }

    fun orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent: OrderCreatedEvent) =
        OrderPaymentEventDTO(
            orderCreatedEvent.order.customerId?.getValue().toString(),
            orderCreatedEvent.order.getId()?.getValue().toString(),
            orderCreatedEvent.order.price!!.getAmount(),
            orderCreatedEvent.createdAt,
            PaymentOrderStatus.PENDING.name
        )

    fun orderCancelledEventToOrderPaymentEventPayload(orderCancelledEvent: OrderCancelledEvent): OrderPaymentEventDTO {
        return OrderPaymentEventDTO(
            orderCancelledEvent.order.customerId?.getValue().toString(),
            orderCancelledEvent.order.getId()?.getValue().toString(),
            orderCancelledEvent.order.price!!.getAmount(),
            orderCancelledEvent.createdAt,
            PaymentOrderStatus.CANCELED.name
        )
    }

    fun orderPaidEventToOrderApprovalEventPayload(orderPaidEvent: OrderPaidEvent): OrderApprovalEventDTO? {
        return OrderApprovalEventDTO(
            orderPaidEvent.order.getId()?.getValue().toString(),
            orderPaidEvent.order.shopId?.getValue().toString(),
            orderPaidEvent.order.price!!.getAmount(),
            orderPaidEvent.createdAt,
            orderPaidEvent.order.items!!.map {
                OrderApprovalEventProduct(UUID.fromString(it.product?.getId()?.getValue().toString()), it.quantity!!)
            },
            ShopOrderStatus.PAID.name,
        )
    }

    fun customerModelToCustomer(customerModel: CustomerModelDTO) = Customer(
        CustomerId(UUID.fromString(customerModel.id)),
        customerModel.username, customerModel.email, customerModel.firstName, customerModel.lastName
    )

    private fun orderAddressToStreetAddress(orderAddressDTO: OrderAddressDTO): StreetAddress =
        StreetAddress(UUID.randomUUID(), orderAddressDTO.streetName, orderAddressDTO.postalCode, orderAddressDTO.city)

    fun orderToTrackOrderResponse(order: Order): TrackOrderResponseDTO =
        TrackOrderResponseDTO(order.trackingId!!.getValue(), order.orderStatus!!, order.errorMessages)
}
