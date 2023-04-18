package co.cp.orderly.shop.domain.core.service

import co.cp.orderly.shop.domain.core.entity.Shop
import co.cp.orderly.shop.domain.core.event.OrderApprovedEvent

interface IShopDomainService {
    fun validateOrder(
        shop: Shop,
        errorMessages: MutableList<String>
    ): OrderApprovedEvent
}
