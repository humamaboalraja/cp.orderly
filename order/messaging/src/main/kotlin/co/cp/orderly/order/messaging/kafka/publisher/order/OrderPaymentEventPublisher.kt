package co.cp.orderly.order.messaging.kafka.publisher.order

import ConsistencyState
import co.cp.orderly.infrastructure.kafka.model.avro.PaymentRequestAvroModel
import co.cp.orderly.infrastructure.kafka.producer.service.KafkaProducer
import co.cp.orderly.infrastructure.kafka.producer.utils.KafkaMessageUtil
import co.cp.orderly.order.domain.application.service.config.OrderApplicationServiceConfig
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentEventDTO
import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment.PaymentRequestMessagePublisher
import co.cp.orderly.order.messaging.mapper.OrderMessagingLayerDateMapper
import org.springframework.stereotype.Component
import java.util.function.BiConsumer
import java.util.logging.Logger

@Component
class OrderPaymentEventPublisher(
    private val orderMessagingDataMapper: OrderMessagingLayerDateMapper,
    private val kafkaProducer: KafkaProducer<String, PaymentRequestAvroModel>,
    private val orderServiceConfigData: OrderApplicationServiceConfig,
    private val kafkaMessageHelper: KafkaMessageUtil
) : PaymentRequestMessagePublisher {

    companion object { private val logger = Logger.getLogger(OrderPaymentEventPublisher::class.java.name) }

    override fun publish(
        orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage,
        consistencyCallback: BiConsumer<OrderPaymentConsistencyMessage, ConsistencyState>
    ) {
        val orderPaymentEventPayload = kafkaMessageHelper.getOrderEventPayload(
            orderPaymentConsistencyMessage.payload, OrderPaymentEventDTO::class.java
        )
        val lltId = orderPaymentConsistencyMessage.lltId.toString()
        logger.info(
            "Received OrderPaymentConsistencyMessage event. " +
                "order #${orderPaymentEventPayload.orderId}, llt #$lltId"
        )
        try {
            val paymentRequestAvroModel = orderMessagingDataMapper
                .orderPaymentEventToPaymentRequestAvroModel(lltId, orderPaymentEventPayload)
            kafkaProducer.send(
                orderServiceConfigData.paymentRequestTopicName,
                lltId,
                paymentRequestAvroModel,
                kafkaMessageHelper.getCallback(
                    orderServiceConfigData.paymentRequestTopicName,
                    paymentRequestAvroModel,
                    orderPaymentConsistencyMessage,
                    consistencyCallback,
                    orderPaymentEventPayload.orderId,
                    "PaymentRequestAvroModel"
                )
            )
            logger.info(
                "OrderPaymentEventPayload has been sent to the message broker. " +
                    "order #${orderPaymentEventPayload.orderId}, llt #$lltId"
            )
        } catch (exception: Exception) {
            logger.info(
                "Something went wrong while sending OrderPaymentEventPayload to the message broker. " +
                    "order #${orderPaymentEventPayload.orderId}, llt #$lltId, Exception #${exception.message}"
            )
        }
    }
}
