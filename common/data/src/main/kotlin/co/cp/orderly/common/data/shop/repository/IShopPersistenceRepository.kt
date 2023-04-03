package co.cp.orderly.common.data.shop.repository

import co.cp.orderly.common.data.shop.entity.ShopEntity
import co.cp.orderly.common.data.shop.entity.ShopEntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IShopPersistenceRepository : JpaRepository<ShopEntity, ShopEntityId> {
    fun findByShopIdAndProductId(shopId: UUID, productId: List<UUID?>?): List<ShopEntity>?
}
