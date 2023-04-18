package co.cp.orderly.payment.domain.core

import co.cp.orderly.payment.domain.core.entity.CreditEntry
import co.cp.orderly.payment.domain.core.entity.CreditHistory
import co.cp.orderly.payment.domain.core.entity.Payment
import co.cp.orderly.payment.domain.core.events.PaymentEvent

interface IPaymentDomainService {
    fun validateAndStartPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistory: MutableList<CreditHistory>,
        errorMessages: MutableList<String>
    ): PaymentEvent

    fun validateAndCancelPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistory: MutableList<CreditHistory>,
        errorMessages: MutableList<String>
    ): PaymentEvent
}
