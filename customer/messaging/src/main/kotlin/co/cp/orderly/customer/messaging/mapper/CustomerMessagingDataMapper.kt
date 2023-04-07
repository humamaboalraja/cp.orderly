package co.cp.orderly.customer.messaging.mapper

import co.cp.orderly.customer.domain.core.event.CustomerCreatedEvent
import co.cp.orderly.kafka.model.avro.CustomerAvroModel
import org.springframework.stereotype.Component

@Component
class CustomerMessagingDataMapper {
    fun paymentResponseAvroModelToPaymentResponse(customerCreatedEvent: CustomerCreatedEvent) =
        CustomerAvroModel(
            customerCreatedEvent.customer.getId()?.getValue().toString(),
            customerCreatedEvent.customer.username,
            customerCreatedEvent.customer.firstName,
            customerCreatedEvent.customer.lastName
        )
}
