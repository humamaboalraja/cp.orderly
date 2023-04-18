package co.cp.orderly.payment.domain.core.events

import co.cp.orderly.payment.domain.core.entity.Payment
import java.time.ZonedDateTime

class PaymentCompletedEvent(
    payment: Payment,
    createdAt: ZonedDateTime,
) : PaymentEvent(payment, createdAt, mutableListOf())
