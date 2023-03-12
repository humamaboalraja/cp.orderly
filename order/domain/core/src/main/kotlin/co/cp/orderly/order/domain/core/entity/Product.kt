package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.ProductId

class Product(
    productId: ProductId,
    var name: String,
    var price: Money,
) : BaseEntity<ProductId>() {

    init { super.setId(productId) }

    fun updateWithVerifiedNameAndPrice(name: String, price: Money) = apply {
        this.name = name
        this.price = price
    }
}
