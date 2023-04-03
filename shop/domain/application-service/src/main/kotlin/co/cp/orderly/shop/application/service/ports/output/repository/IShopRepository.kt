package co.cp.orderly.shop.application.service.ports.output.repository

import co.cp.orderly.shop.domain.core.entity.Shop

interface IShopRepository {
    fun findShopDetails(shop: Shop): Shop?
}
