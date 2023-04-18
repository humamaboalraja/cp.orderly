package co.cp.orderly.payment.domain.core.events

import co.cp.orderly.payment.domain.core.entity.Payment
import java.time.ZonedDateTime

class PaymentFailedEvent(
    payment: Payment,
    createdAt: ZonedDateTime,
    errorMessages: MutableList<String>,
) : PaymentEvent(payment, createdAt, errorMessages)
