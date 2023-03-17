package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.ProductId
import java.math.BigDecimal

class Product(
    productId: ProductId,
    var name: String? = null,
    var price: Money? = null,
) : BaseEntity<ProductId>() {

    constructor(productId: ProductId, price: BigDecimal) : this(productId, null, Money(price))

    init { super.setId(productId) }

    fun updateWithVerifiedNameAndPrice(name: String, price: Money) = apply {
        this.name = name
        this.price = price
    }
}
