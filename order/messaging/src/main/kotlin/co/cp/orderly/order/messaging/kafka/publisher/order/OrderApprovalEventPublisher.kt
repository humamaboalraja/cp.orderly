package co.cp.orderly.order.messaging.kafka.publisher.order

import ConsistencyState
import co.cp.orderly.infrastructure.kafka.model.avro.ShopApprovalRequestAvroModel
import co.cp.orderly.infrastructure.kafka.producer.service.KafkaProducer
import co.cp.orderly.infrastructure.kafka.producer.utils.KafkaMessageUtil
import co.cp.orderly.order.domain.application.service.config.OrderApplicationServiceConfig
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalEventDTO
import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.shop.ShopApprovalRequestMessagePublisher
import co.cp.orderly.order.messaging.mapper.OrderMessagingLayerDateMapper
import org.springframework.stereotype.Component
import java.util.function.BiConsumer
import java.util.logging.Logger

@Component
class OrderApprovalEventPublisher(
    private val orderMessagingDataMapper: OrderMessagingLayerDateMapper,
    private val kafkaProducer: KafkaProducer<String, ShopApprovalRequestAvroModel>,
    private val orderServiceConfigData: OrderApplicationServiceConfig,
    private val kafkaMessageHelper: KafkaMessageUtil
) : ShopApprovalRequestMessagePublisher {

    companion object { private val logger = Logger.getLogger(OrderApprovalEventPublisher::class.java.name) }

    override fun publish(
        orderApprovalConsistencyMessage: OrderApprovalConsistencyMessage,
        outboxCallback: BiConsumer<OrderApprovalConsistencyMessage, ConsistencyState>
    ) {
        val orderApprovalEventPayload = kafkaMessageHelper.getOrderEventPayload(
            orderApprovalConsistencyMessage.payload, OrderApprovalEventDTO::class.java
        )
        val lltId = orderApprovalConsistencyMessage.lltId.toString()
        logger.info(
            "Received OrderApprovalConsistencyMessage. order #${orderApprovalEventPayload.orderId}, llt #$lltId"
        )
        try {
            val shopApprovalRequestAvroModel = orderMessagingDataMapper
                .orderApprovalEventToShopApprovalRequestAvroModel(lltId, orderApprovalEventPayload)
            kafkaProducer.send(
                orderServiceConfigData.shopApprovalRequestTopicName,
                lltId,
                shopApprovalRequestAvroModel,
                kafkaMessageHelper.getCallback(
                    orderServiceConfigData.shopApprovalRequestTopicName,
                    shopApprovalRequestAvroModel,
                    orderApprovalConsistencyMessage,
                    outboxCallback,
                    orderApprovalEventPayload.orderId,
                    "ShopApprovalRequestAvroModel"
                )
            )
            logger.info(
                "OrderApprovalEventPayload sent to the Message Broker. " +
                    "order #${shopApprovalRequestAvroModel.orderId}, llt #$lltId"
            )
        } catch (exception: Exception) {
            logger.info(
                "Something went wrong while sending OrderApprovalEventPayload to the Message Broker. " +
                    "order #${orderApprovalEventPayload.orderId},llt  #$lltId, Exception: #${exception.message}"
            )
        }
    }
}
