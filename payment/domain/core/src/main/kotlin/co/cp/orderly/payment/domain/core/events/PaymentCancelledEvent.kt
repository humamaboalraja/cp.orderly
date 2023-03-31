package co.cp.orderly.payment.domain.core.events

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.payment.domain.core.entity.Payment
import java.time.ZonedDateTime

class PaymentCancelledEvent(
    payment: Payment,
    createdAt: ZonedDateTime,
    errorMessages: MutableList<String>? = null,
    private val paymentCanceledDomainEventPublisher: DomainEventPublisher<PaymentCancelledEvent>
) : PaymentEvent(payment, createdAt, mutableListOf()) {
    override fun fire() = run { paymentCanceledDomainEventPublisher.publish(this) }
}
