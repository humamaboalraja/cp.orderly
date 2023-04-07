package co.cp.orderly.customer.messaging.kafka

import co.cp.orderly.customer.domain.core.event.CustomerCreatedEvent
import co.cp.orderly.customer.messaging.mapper.CustomerMessagingDataMapper
import co.cp.orderly.infrastructure.kafka.producer.service.KafkaProducer
import co.cp.orderly.kafka.model.avro.CustomerAvroModel
import cp.cp.orderly.customer.domain.application.service.config.CustomerApplicationServiceConfig
import cp.cp.orderly.customer.domain.application.service.ports.output.messaging.ICustomerMessagePublisher
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback
import java.util.logging.Logger

@Component
class CustomerCreatedEventKafkaPublisher(
    private val customerMessagingDataMapper: CustomerMessagingDataMapper,
    private val kafkaProducer: KafkaProducer<String, CustomerAvroModel>,
    private val customerApplicationServiceConfig: CustomerApplicationServiceConfig
) : ICustomerMessagePublisher {

    companion object { private val logger = Logger.getLogger(CustomerCreatedEventKafkaPublisher::class.java.name) }

    override fun publish(customerCreatedEvent: CustomerCreatedEvent) {
        logger.info(
            "Received CustomerCreatedEvent for customer #${customerCreatedEvent.customer.getId()?.getValue()}"
        )
        try {
            val customerAvroModel = customerMessagingDataMapper
                .paymentResponseAvroModelToPaymentResponse(customerCreatedEvent)
            kafkaProducer.send(
                customerApplicationServiceConfig.customerTopicName, customerAvroModel.id,
                customerAvroModel,
                getCallback(customerApplicationServiceConfig.customerTopicName, customerAvroModel)
            )
            logger.info(
                "CustomerCreatedEvent sent to kafka for customer #${customerAvroModel.id}",

            )
        } catch (e: Exception) {
            logger.info(
                "Error while sending CustomerCreatedEvent to kafka " +
                    "for customer #${customerCreatedEvent.customer.getId()?.getValue()}," +
                    " error: {e.message}",
            )
        }
    }

    private fun getCallback(
        topicName: String,
        message: CustomerAvroModel
    ): ListenableFutureCallback<SendResult<String, CustomerAvroModel>> {
        return object : ListenableFutureCallback<SendResult<String, CustomerAvroModel>> {
            override fun onFailure(throwable: Throwable) {
                logger.info(
                    "Error while sending message $message to topic $topicName" +
                        "exception: $throwable"
                )
            }

            override fun onSuccess(result: SendResult<String, CustomerAvroModel>?) {
                val metadata = result?.recordMetadata
                logger.info(
                    "Received new metadata. Topic: ${metadata?.topic()}; Partition ${metadata?.partition()}; " +
                        "Offset ${metadata?.offset()}; Timestamp ${metadata?.timestamp()}, " +
                        "at ${System.nanoTime()}",
                )
            }
        }
    }
}
