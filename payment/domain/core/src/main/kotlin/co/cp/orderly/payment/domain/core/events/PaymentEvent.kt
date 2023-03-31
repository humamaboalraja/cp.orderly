package co.cp.orderly.payment.domain.core.events

import co.cp.orderly.domain.event.DomainEvent
import co.cp.orderly.payment.domain.core.entity.Payment
import java.time.ZonedDateTime

abstract class PaymentEvent(
    val payment: Payment,
    val createdAt: ZonedDateTime,
    val errorMessages: MutableList<String>
) : DomainEvent<Payment>
