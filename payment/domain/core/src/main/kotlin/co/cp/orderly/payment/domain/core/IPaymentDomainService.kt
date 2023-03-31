package co.cp.orderly.payment.domain.core

import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.payment.domain.core.entity.CreditEntry
import co.cp.orderly.payment.domain.core.entity.CreditHistory
import co.cp.orderly.payment.domain.core.entity.Payment
import co.cp.orderly.payment.domain.core.events.PaymentCancelledEvent
import co.cp.orderly.payment.domain.core.events.PaymentCompletedEvent
import co.cp.orderly.payment.domain.core.events.PaymentEvent
import co.cp.orderly.payment.domain.core.events.PaymentFailedEvent

interface IPaymentDomainService {
    fun validateAndStartPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistory: MutableList<CreditHistory>,
        errorMessages: MutableList<String>,
        paymentCompletedDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>,
        paymentFailedDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>,
    ): PaymentEvent

    fun validateAndCancelPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistory: MutableList<CreditHistory>,
        errorMessages: MutableList<String>,
        paymentCanceledDomainEventPublisher: DomainEventPublisher<PaymentCancelledEvent>,
        paymentFailedDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>,
    ): PaymentEvent
}
