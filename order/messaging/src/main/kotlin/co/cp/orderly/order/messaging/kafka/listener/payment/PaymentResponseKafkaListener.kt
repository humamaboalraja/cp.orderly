package co.cp.orderly.order.messaging.kafka.listener.payment

import co.cp.orderly.infrastructure.kafka.model.avro.PaymentResponseAvroModel
import co.cp.orderly.infrastructure.kafka.model.avro.PaymentStatus
import co.cp.orderly.kafka.model.consumer.IKafkaConsumer
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.payment.PaymentResponseMessageListener
import co.cp.orderly.order.domain.core.exception.OrderNotFoundException
import co.cp.orderly.order.messaging.mapper.OrderMessagingLayerDateMapper
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.function.Consumer
import java.util.logging.Logger

@Component
class PaymentResponseKafkaListener(
    private val paymentResponseMessageListener: PaymentResponseMessageListener,
    private val orderMessagingDataMapper: OrderMessagingLayerDateMapper,
) : IKafkaConsumer<PaymentResponseAvroModel> {

    companion object { private val logger = Logger.getLogger(PaymentResponseKafkaListener::class.java.name) }

    @KafkaListener(
        id = "\${kafka-consumer-config.payment-consumer-group-id}",
        topics = ["\${order-service.payment-response-topic-name}"]
    )
    override fun receive(
        @Payload messages: List<PaymentResponseAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info(
            "#${messages.size} payment responses were received. " +
                "keys #$keys, partitions #$partitions, offsets #$offsets"
        )
        messages.forEach(
            Consumer {
                try {
                    when {
                        PaymentStatus.COMPLETED === it.paymentStatus -> {
                            logger.info("Processing successful payment. order #${it.orderId}")
                            paymentResponseMessageListener.paymentCompleted(
                                orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(it)
                            )
                        }
                        PaymentStatus.CANCELLED === it.paymentStatus || PaymentStatus.FAILED === it.paymentStatus -> {
                            logger.info("Processing unsuccessful payment. order #${it.orderId}")
                            paymentResponseMessageListener.paymentCancelled(
                                orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(it)
                            )
                        }
                    }
                } catch (exception: OptimisticLockingFailureException) {
                    logger.info("Optimistic locking exception was thrown in ${this::class}. order #${it.orderId}")
                } catch (exception: OrderNotFoundException) { logger.info("Order wasn't found for  #${it.orderId}") }
            }
        )
    }
}
