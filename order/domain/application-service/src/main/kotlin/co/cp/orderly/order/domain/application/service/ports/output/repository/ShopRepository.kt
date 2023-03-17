package co.cp.orderly.order.domain.application.service.ports.output.repository

import co.cp.orderly.order.domain.core.entity.Shop

interface ShopRepository {

    fun getShopDetails(shop: Shop): Shop?
}
