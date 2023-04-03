package co.cp.orderly.order.data.shop.adapter

import co.cp.orderly.common.data.shop.repository.IShopPersistenceRepository
import co.cp.orderly.order.data.shop.mapper.ShopDataMapper
import co.cp.orderly.order.domain.application.service.ports.output.repository.ShopRepository
import co.cp.orderly.order.domain.core.entity.Shop
import org.springframework.stereotype.Component

@Component
class ShopRepositoryImpl(
    private val shopDataMapper: ShopDataMapper,
    private val shopRepository: IShopPersistenceRepository
) : ShopRepository {
    override fun getShopDetails(shop: Shop): Shop? {
        val shopProducts = shopDataMapper.shopToShopProducts(shop)
        val shopEntities = shopRepository.findByShopIdAndProductId(shop.getId()!!.getValue(), shopProducts)
        return shopEntities?.let { shopDataMapper.shopEntityToShop(it) }
    }
}
