package co.cp.orderly.infrastructure.kafka.producer.service

import co.cp.orderly.infrastructure.kafka.producer.exception.KafkaProducerException
import jakarta.annotation.PreDestroy
import org.apache.avro.specific.SpecificRecordBase
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.concurrent.CompletableFuture
import java.util.logging.Logger

@Component
class KafkaProducerImpl<K : Serializable, V : SpecificRecordBase>(
    private val kafkaTemplate: KafkaTemplate<K, V>
) : KafkaProducer<K, V> {
    companion object { private val logger = Logger.getLogger(KafkaProducerImpl::class.java.name) }

    override fun send(
        topicName: String,
        key: K,
        message: V,
    ): () -> CompletableFuture<SendResult<K, V>> {
        logger.info("Sending this message $message to $topicName")
        try {

            return { kafkaTemplate.send(topicName, key, message) }
        } catch (ex: Exception) {
            logger.severe("Something went wrong with Kafka's producer $key, message: $message, Exception: $ex")
            throw KafkaProducerException("Something went wrong with Kafka's producer $key, message: $message, ")
        }
    }

    @PreDestroy
    fun close() {
        kafkaTemplate.let {
            logger.info("Shutting down kafka")
            kafkaTemplate.destroy()
        }
    }
}
