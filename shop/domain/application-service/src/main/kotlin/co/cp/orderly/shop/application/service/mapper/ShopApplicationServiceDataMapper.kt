package co.cp.orderly.shop.application.service.mapper

import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.shop.application.service.consistency.model.OrderEventPayloadDTO
import co.cp.orderly.shop.application.service.dto.ShopApprovalRequest
import co.cp.orderly.shop.domain.core.entity.OrderData
import co.cp.orderly.shop.domain.core.entity.Product
import co.cp.orderly.shop.domain.core.entity.Shop
import co.cp.orderly.shop.domain.core.event.OrderApprovalEvent
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ShopApplicationServiceDataMapper {
    fun shopApprovalRequestToShop(shopApprovalRequest: ShopApprovalRequest): Shop =
        Shop.builder()
            .shopId(ShopId(UUID.fromString(shopApprovalRequest.id)))
            .orderData(
                OrderData.builder().orderId(OrderId(UUID.fromString(shopApprovalRequest.orderId)))
                    .products(
                        shopApprovalRequest.products?.map {
                            Product(productId = it.productId, quantity = it.quantity)
                        }?.toList()
                    )
                    .totalAmount(Money(shopApprovalRequest.price!!))
                    .orderStatus(OrderStatus.valueOf(shopApprovalRequest.shopOrderStatus?.name!!)).build()
            )
            .build()

    fun orderApprovalEventToOrderEventPayloadDTO(orderApprovalEvent: OrderApprovalEvent) =
        OrderEventPayloadDTO(
            orderApprovalEvent.orderApproval.orderId?.getValue().toString(),
            orderApprovalEvent.shopId.getValue().toString(),
            orderApprovalEvent.createdAt,
            orderApprovalEvent.orderApproval.orderApprovalStatus?.name!!,
            orderApprovalEvent.errorMessages
        )
}
