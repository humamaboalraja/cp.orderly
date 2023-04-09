package co.cp.orderly.customer.messaging.kafka

import co.cp.orderly.customer.domain.core.event.CustomerCreatedEvent
import co.cp.orderly.customer.messaging.mapper.CustomerMessagingDataMapper
import cp.cp.orderly.customer.domain.application.service.config.CustomerApplicationServiceConfig
import cp.cp.orderly.customer.domain.application.service.ports.output.messaging.ICustomerMessagePublisher
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class CustomerCreatedEventKafkaPublisher(
    private val customerMessagingDataMapper: CustomerMessagingDataMapper,
    // private val kafkaProducer: KafkaProducer<String, CustomerAvroModel>,
    private val customerApplicationServiceConfig: CustomerApplicationServiceConfig
) : ICustomerMessagePublisher {

    companion object { private val logger = Logger.getLogger(CustomerCreatedEventKafkaPublisher::class.java.name) }

    override fun publish(customerCreatedEvent: CustomerCreatedEvent) {
        logger.info(
            "Received CustomerCreatedEvent for customer #${customerCreatedEvent.customer.getId()?.getValue()}"
        )
        try {
            logger.info(
                "CustomerCreatedEvent sent to kafka for customer #customerAvroModel.id",

            )
        } catch (e: Exception) {
            logger.info(
                "Error while sending CustomerCreatedEvent to kafka " +
                    "for customer #${customerCreatedEvent.customer.getId()?.getValue()}," +
                    " error: {e.message}",
            )
        }
    }
}
