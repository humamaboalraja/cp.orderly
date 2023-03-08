package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.ProductId

class Product(
    productId: ProductId,
    val name: String,
    val price: Money,
) : BaseEntity<ProductId>() {

    init { super.setId(productId) }
}
