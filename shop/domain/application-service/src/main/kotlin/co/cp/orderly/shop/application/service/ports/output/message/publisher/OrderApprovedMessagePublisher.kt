package co.cp.orderly.shop.application.service.ports.output.message.publisher

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.shop.domain.core.event.OrderApprovedEvent

interface OrderApprovedMessagePublisher : DomainEventPublisher<OrderApprovedEvent>
