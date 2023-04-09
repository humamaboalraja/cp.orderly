package co.cp.orderly.customer.domain.core.event

import co.cp.orderly.customer.domain.core.entity.Customer
import co.cp.orderly.domain.event.DomainEvent
import java.time.ZonedDateTime

class CustomerCreatedEvent(
    val customer: Customer,
    val createdAt: ZonedDateTime
) : DomainEvent<Customer>
