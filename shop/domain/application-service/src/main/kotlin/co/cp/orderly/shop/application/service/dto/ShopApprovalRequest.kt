package co.cp.orderly.shop.application.service.dto

import co.cp.orderly.domain.vos.ShopOrderStatus
import co.cp.orderly.shop.domain.core.entity.Product
import java.math.BigDecimal
import java.time.Instant

data class ShopApprovalRequest(
    var id: String? = null,
    val sagaId: String? = null,
    val shopId: String? = null,
    val orderId: String? = null,
    val shopOrderStatus: ShopOrderStatus? = null,
    val products: List<Product>? = null,
    val price: BigDecimal? = null,
    val createdAt: Instant? = null
)
