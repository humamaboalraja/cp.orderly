package co.cp.orderly.shop.domain.core.event

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.shop.domain.core.entity.OrderApproval
import java.time.ZonedDateTime

class OrderApprovedEvent(
    override var orderApproval: OrderApproval,
    override var shopId: ShopId,
    override var errorMessages: MutableList<String>,
    override var createdAt: ZonedDateTime,
    var domainEventPublisher: DomainEventPublisher<OrderApprovedEvent>
) : OrderApprovalEvent(
    orderApproval, shopId, errorMessages, createdAt
)
