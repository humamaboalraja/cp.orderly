package co.cp.orderly.order.data.shop.mapper

import co.cp.orderly.common.data.shop.entity.ShopEntity
import co.cp.orderly.common.data.shop.exception.ShopDataException
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.order.domain.core.entity.Product
import co.cp.orderly.order.domain.core.entity.Shop
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ShopDataMapper {
    fun shopToShopProducts(shop: Shop): List<UUID?>? =
        shop.products?.map { it.getId()?.getValue() }?.toList()

    fun shopEntityToShop(shopEntities: List<ShopEntity>): Shop {
        val shopEntity = shopEntities.first() ?: throw(ShopDataException("Couldn't find the shop"))
        return Shop.builder()
            .shopId(ShopId(shopEntity.shopId!!))
            .products(
                shopEntities.map {
                    Product(ProductId(it.productId!!), it.productName, Money(it.productPrice!!))
                }.toList()
            )
            .active(shopEntity.isActive).build()
    }
}
