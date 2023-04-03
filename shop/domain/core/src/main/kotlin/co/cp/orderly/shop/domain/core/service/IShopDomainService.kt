package co.cp.orderly.shop.domain.core.service

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.shop.domain.core.entity.Shop
import co.cp.orderly.shop.domain.core.event.OrderApprovedEvent
import co.cp.orderly.shop.domain.core.event.OrderRejectedEvent

interface IShopDomainService {
    fun validateOrder(
        shop: Shop,
        errorMessages: MutableList<String>,
        orderApprovedDomainEventPublisher: DomainEventPublisher<OrderApprovedEvent>,
        orderRejectedDomainEventPublisher: DomainEventPublisher<OrderRejectedEvent>,
    ): OrderApprovedEvent
}
