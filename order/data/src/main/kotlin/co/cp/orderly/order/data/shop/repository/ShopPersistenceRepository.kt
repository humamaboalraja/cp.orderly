package co.cp.orderly.order.data.shop.repository

import co.cp.orderly.order.data.shop.entity.ShopEntity
import co.cp.orderly.order.data.shop.entity.ShopEntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ShopPersistenceRepository : JpaRepository<ShopEntity, ShopEntityId> {
    fun findByShopIdAndProductId(shopId: UUID, productId: List<UUID?>?): List<ShopEntity>?
}
