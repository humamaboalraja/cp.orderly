package co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.order.domain.core.event.OrderCreatedEvent

interface OrderCreatedPaymentRequestMessagePublisher : DomainEventPublisher<OrderCreatedEvent>
