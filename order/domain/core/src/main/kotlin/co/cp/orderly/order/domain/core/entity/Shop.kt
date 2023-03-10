package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.AggregateRoot
import co.cp.orderly.domain.vos.ShopId

class Shop private constructor(
    shopId: ShopId?,
    val products: List<Product>?,
    val active: Boolean?
) : AggregateRoot<ShopId>() {

    init { shopId?.let { super.setId(it) } }

    companion object { fun builder(): Builder = Builder() }

    data class Builder(
        private var shopId: ShopId? = null,
        private var products: List<Product>? = null,
        private var active: Boolean? = null
    ) {
        fun shopId(shopId: ShopId?) = apply { this.shopId = shopId }
        fun products(products: List<Product>?) = apply { this.products = products }
        fun active(active: Boolean?) = apply { this.active = active }
        fun build() = Shop(
            shopId, products, active
        )
    }
}
