package co.cp.orderly.order.messaging.kafka.listener.shop

import co.cp.orderly.infrastructure.kafka.model.avro.OrderApprovalStatus
import co.cp.orderly.infrastructure.kafka.model.avro.ShopApprovalResponseAvroModel
import co.cp.orderly.kafka.model.consumer.IKafkaConsumer
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.shop.ShopApprovalResponseMessageListener
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
class ShopApprovalResponseKafkaListener(
    private val shopApprovalResponseMessageListener: ShopApprovalResponseMessageListener,
    private val orderMessagingDataMapper: OrderMessagingLayerDateMapper,
) : IKafkaConsumer<ShopApprovalResponseAvroModel> {

    companion object { private val logger = Logger.getLogger(ShopApprovalResponseKafkaListener::class.java.name) }

    @KafkaListener(
        id = "\${kafka-consumer-config.payment-consumer-group-id}",
        topics = ["\${order-service.payment-response-topic-name}"]
    )
    @KafkaListener(
        id = "\${kafka-consumer-config.shop-approval-consumer-group-id}",
        topics = ["\${order-service.shop-approval-response-topic-name}"]
    )

    override fun receive(
        @Payload messages: List<ShopApprovalResponseAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info(
            "#${messages.size} shop approval responses were received. " +
                "keys #$keys, partitions #$partitions, offsets #$offsets"
        )
        messages.forEach(
            Consumer {
                try {
                    when {
                        OrderApprovalStatus.APPROVED === it.getOrderApprovalStatus() -> {

                            logger.info("Processing approved order. order #${it.orderId}")
                            shopApprovalResponseMessageListener.orderApproved(
                                orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(it)
                            )
                        }
                        OrderApprovalStatus.REJECTED === it.getOrderApprovalStatus() -> {
                            logger.info(
                                "Processing rejected order for order #${it.orderId}. " +
                                    "Error messages #${it.errorMessages.joinToString { "," }}"
                            )
                            shopApprovalResponseMessageListener.orderRejected(
                                orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(it)
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
