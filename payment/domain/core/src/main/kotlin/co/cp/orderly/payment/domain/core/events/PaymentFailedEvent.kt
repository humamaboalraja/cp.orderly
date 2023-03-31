package co.cp.orderly.payment.domain.core.events

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.payment.domain.core.entity.Payment
import java.time.ZonedDateTime

class PaymentFailedEvent(
    payment: Payment,
    createdAt: ZonedDateTime,
    errorMessages: MutableList<String>,
    private val paymentFailedDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>

) : PaymentEvent(payment, createdAt, errorMessages) {
    override fun fire() = run { paymentFailedDomainEventPublisher.publish(this) }
}
