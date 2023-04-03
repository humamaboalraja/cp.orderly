package co.cp.orderly.shop.data.adapter

import co.cp.orderly.common.data.shop.repository.IShopPersistenceRepository
import co.cp.orderly.shop.application.service.ports.output.repository.IShopRepository
import co.cp.orderly.shop.data.mapper.ShopDataLayerDataMapper
import co.cp.orderly.shop.domain.core.entity.Shop
import org.springframework.stereotype.Component

@Component
class ShopRepositoryImpl(
    private val shopPersistenceRepository: IShopPersistenceRepository,
    private val shopDataLayerDataMapper: ShopDataLayerDataMapper
) : IShopRepository {
    override fun findShopDetails(shop: Shop): Shop? {
        val shopProducts = shopDataLayerDataMapper.shopToShopProducts(shop);
        val shopEntities = shopPersistenceRepository.findByShopIdAndProductId(shop.getId()!!.getValue(), shopProducts)
        return shopEntities?.map { shopDataLayerDataMapper.shopEntityToShop(listOf(it)) }?.first()  
    }
}
