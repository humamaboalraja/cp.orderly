package co.cp.orderly.payment.domain.core.events

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.payment.domain.core.entity.Payment
import java.time.ZonedDateTime

class PaymentCompletedEvent(
    payment: Payment,
    createdAt: ZonedDateTime,
    errorMessages: MutableList<String>? = null,
    private val paymentCompletedDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>
) : PaymentEvent(payment, createdAt, mutableListOf())
