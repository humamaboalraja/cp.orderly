package co.cp.orderly.payment.domain.service.ports.outptut.message.publisher

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.payment.domain.core.events.PaymentCancelledEvent

interface IPaymentCanceledMessagePublisher : DomainEventPublisher<PaymentCancelledEvent>
