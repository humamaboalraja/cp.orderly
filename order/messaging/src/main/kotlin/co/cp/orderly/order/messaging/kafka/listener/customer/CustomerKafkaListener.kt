package co.cp.orderly.order.messaging.kafka.listener.customer

import co.cp.orderly.infrastructure.kafka.model.avro.CustomerAvroModel
import co.cp.orderly.kafka.model.consumer.IKafkaConsumer
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.customer.CustomerMessageListener
import co.cp.orderly.order.messaging.mapper.OrderMessagingLayerDateMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.function.Consumer
import java.util.logging.Logger

@Component
class CustomerKafkaListener(
    private val customerMessageListener: CustomerMessageListener,
    private val orderMessagingDataMapper: OrderMessagingLayerDateMapper,
) : IKafkaConsumer<CustomerAvroModel> {

    companion object { private val logger = Logger.getLogger(CustomerKafkaListener::class.java.name) }

    @KafkaListener(id = "\${kafka-consumer-config.customer-group-id}", topics = ["\${order-service.customer-topic-name}"])
    override fun receive(
        @Payload messages: List<CustomerAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info(
            "#${messages.size} customerCreate messages were received. " +
                "keys #$keys, partitions #$partitions, offsets #$offsets"
        )
        messages.forEach(
            Consumer {
                customerMessageListener.customerCreated(orderMessagingDataMapper.customerAvroModelToCustomerModel(it!!))
            }
        )
    }
}
