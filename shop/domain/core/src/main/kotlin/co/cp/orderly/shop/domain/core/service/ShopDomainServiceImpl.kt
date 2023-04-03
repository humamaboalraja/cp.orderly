package co.cp.orderly.shop.domain.core.service

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.domain.vos.OrderApprovalStatus
import co.cp.orderly.shop.domain.core.entity.Shop
import co.cp.orderly.shop.domain.core.event.OrderApprovedEvent
import co.cp.orderly.shop.domain.core.event.OrderRejectedEvent
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.logging.Logger

class ShopDomainServiceImpl : IShopDomainService {

    companion object { private val logger = Logger.getLogger(ShopDomainServiceImpl::class.java.name) }

    override fun validateOrder(
        shop: Shop,
        errorMessages: MutableList<String>,
        orderApprovedDomainEventPublisher: DomainEventPublisher<OrderApprovedEvent>,
        orderRejectedDomainEventPublisher: DomainEventPublisher<OrderRejectedEvent>
    ): OrderApprovedEvent {
        shop.validateOrder(errorMessages)
        logger.info("Validating order #${shop.orderData?.getId()?.getValue()}")
        return when (errorMessages.isEmpty()) {
            true -> {
                logger.info("Order #${shop.orderData?.getId()?.getValue()}'s is approved")
                shop.startOrderApproval(OrderApprovalStatus.APPROVED)
                OrderApprovedEvent(
                    shop.orderApproval!!,
                    shop.getId()!!,
                    errorMessages,
                    ZonedDateTime.now(ZoneId.of("UTC")),
                    orderApprovedDomainEventPublisher
                )
            }
            else -> {
                logger.info("Order #${shop.orderData?.getId()?.getValue()}'s is rejected")
                shop.startOrderApproval(OrderApprovalStatus.REJECTED)
                OrderApprovedEvent(
                    shop.orderApproval!!,
                    shop.getId()!!,
                    errorMessages,
                    ZonedDateTime.now(ZoneId.of("UTC")),
                    orderApprovedDomainEventPublisher
                )
            }
        }
    }
}
