package co.cp.orderly.common.data.shop.entity

import java.io.Serializable
import java.util.UUID

data class ShopEntityId(var shopId: UUID?, var productId: UUID?) : Serializable
