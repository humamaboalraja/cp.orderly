package co.cp.orderly.shop.domain.core.entity

import co.cp.orderly.domain.entity.BaseEntity
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.ProductId

class Product(
    var productId: ProductId? = null,
    var name: String? = null,
    var price: Money? = null,
    final val quantity: Int? = null,
    var isAvailable: Boolean? = null,
) : BaseEntity<ProductId>() {

    init { productId?.let { super.setId(it) } }

    fun updateProductStateToConfirmed(name: String?, price: Money?, isAvailable: Boolean?) {
        this.name = name
        this.price = price
        this.isAvailable = isAvailable
    }
}
