package co.cp.orderly.order.domain.application.service.ports.output.message.publisher.shop

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.order.domain.core.event.OrderPaidEvent

interface OrderPaidShopRequestMessagePublisher : DomainEventPublisher<OrderPaidEvent>
