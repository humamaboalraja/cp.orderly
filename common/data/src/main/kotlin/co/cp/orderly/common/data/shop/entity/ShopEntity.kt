package co.cp.orderly.common.data.shop.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.Objects
import java.util.UUID

@Entity
@IdClass(ShopEntityId::class)
@Table(name = "order_shop_materialized_view", schema = "shop")
class ShopEntity(
    @Id
    var shopId: UUID?,
    @Id
    var productId: UUID?,
    var shopName: String?,
    var isActive: Boolean?,
    var productName: String?,
    var productPrice: BigDecimal?,
    var productAvailable: Boolean?
) {
    constructor() : this(
        null, null, null, null, null, null, null
    )

    override fun equals(other: Any?): Boolean {
        other as ShopEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> shopId == other.shopId && productId == other.productId
        }
    }

    override fun hashCode(): Int = Objects.hash(shopId, productName)
}
