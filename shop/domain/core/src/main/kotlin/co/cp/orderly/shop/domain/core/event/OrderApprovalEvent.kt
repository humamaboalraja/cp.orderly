package co.cp.orderly.shop.domain.core.event

import co.cp.orderly.domain.event.DomainEvent
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.shop.domain.core.entity.OrderApproval
import java.time.ZonedDateTime

abstract class OrderApprovalEvent(
    open var orderApproval: OrderApproval,
    open var shopId: ShopId,
    open var errorMessages: MutableList<String>,
    open var createdAt: ZonedDateTime
) : DomainEvent<OrderApproval>
