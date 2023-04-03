package co.cp.orderly.shop.data.mapper

import co.cp.orderly.common.data.shop.entity.ShopEntity
import co.cp.orderly.common.data.shop.exception.ShopDataException
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.shop.data.entity.OrderApprovalEntity
import co.cp.orderly.shop.domain.core.entity.OrderApproval
import co.cp.orderly.shop.domain.core.entity.OrderData
import co.cp.orderly.shop.domain.core.entity.Product
import co.cp.orderly.shop.domain.core.entity.Shop
import co.cp.orderly.shop.domain.core.vos.OrderApprovalId
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ShopDataLayerDataMapper {

    fun convertOrderApprovalEntityToOrderApproval(orderApprovalEntity: OrderApprovalEntity): OrderApproval =
        OrderApproval.Builder()
            .orderApprovalId(OrderApprovalId(orderApprovalEntity.orderApprovalId!!))
            .shopId(ShopId(orderApprovalEntity.shopId!!))
            .orderId(OrderId(orderApprovalEntity.orderId!!))
            .orderApprovalStatus(orderApprovalEntity.status)
            .build()

    fun shopToShopProducts(shop: Shop): List<UUID> =
        shop.orderData?.products!!.map { it.getId()!!.getValue() }.toList()

    fun orderApprovalToOrderApprovalEntity(orderApproval: OrderApproval): OrderApprovalEntity =
        OrderApprovalEntity(
            orderApproval.getId()?.getValue(),
            orderApproval.shopId?.getValue(),
            orderApproval.orderId?.getValue(),
            orderApproval.orderApprovalStatus
        )

    fun shopEntityToShop(shopEntities: List<ShopEntity>): Shop {
        val shopEntity = shopEntities.firstOrNull()
            .apply { if (this == null) throw ShopDataException("Shop not found") }
        val shopProducts = shopEntities.map {
            Product(
                ProductId(it.productId!!), it.productName, Money(it.productPrice!!), isAvailable = it.productAvailable
            )
        }.toList()

        return Shop.builder()
            .shopId(ShopId(shopEntity?.shopId!!))
            .orderData(OrderData.builder().products(shopProducts).build())
            .isActive(shopEntity.isActive).build()
    }
}
