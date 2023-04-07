package co.cp.orderly.infrastructure.kafka.producer.utils

import ConsistencyState
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback
import java.util.function.BiConsumer
import java.util.logging.Logger

@Component
class KafkaMessageUtil(private val objectMapper: ObjectMapper) {
    companion object { private val logger = Logger.getLogger(KafkaMessageUtil::class.java.name) }

    fun <T> getOrderEventPayload(payload: String?, outputType: Class<T>): T {
        return try {
            objectMapper.readValue(payload, outputType)
        } catch (exception: JsonProcessingException) {
            logger.info("Could not read ${outputType.name}. Exception: exception")
            throw OrderDomainException("Could not read ${outputType.name} object", exception)
        }
    }

    fun <T, U> getKafkaCallback(
        responseTopicName: String?,
        avroModel: T,
        outboxMessage: U,
        consistencyCallback: BiConsumer<U, ConsistencyState?>,
        orderId: String?,
        avroModelName: String?
    ): ListenableFutureCallback<SendResult<String?, T>?> {
        return object : ListenableFutureCallback<SendResult<String?, T>?> {
            override fun onFailure(exception: Throwable) {
                logger.info(
                    "Error while sending $avroModelName. message: {$avroModel.toString()} " +
                        "and consistency type: ${outboxMessage!!::class.java} to topic $responseTopicName" +
                        "exception: $exception"
                )
                consistencyCallback.accept(outboxMessage, ConsistencyState.FAILED)
            }

            override fun onSuccess(result: SendResult<String?, T>?) {
                val metadata = result?.recordMetadata
                logger.info(
                    "Received successful response from Kafka for order id: $orderId" +
                        " Topic: ${metadata?.topic()} Partition: ${metadata?.partition()} " +
                        "Offset: ${metadata?.offset()} Timestamp: ${metadata?.timestamp()}",
                )
                consistencyCallback.accept(outboxMessage, ConsistencyState.COMPLETED)
            }
        }
    }
}
