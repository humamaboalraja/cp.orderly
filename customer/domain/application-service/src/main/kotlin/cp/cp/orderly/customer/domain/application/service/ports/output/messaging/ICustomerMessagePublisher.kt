package cp.cp.orderly.customer.domain.application.service.ports.output.messaging

import co.cp.orderly.customer.domain.core.event.CustomerCreatedEvent

interface ICustomerMessagePublisher {
    fun publish(customerCreatedEvent: CustomerCreatedEvent)
}
