package co.cp.orderly.infrastructure.kafka.producer.config

import co.cp.orderly.kafka.cofig.KafkaConfig
import co.cp.orderly.kafka.cofig.KafkaProducerConfigData
import org.apache.avro.specific.SpecificRecordBase
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import java.io.Serializable

@Configuration
open class KafkaProducerConfig<K : Serializable, V : SpecificRecordBase>(
    private val kafkaConfig: KafkaConfig? = null,
    private val kafkaProducerConfigData: KafkaProducerConfigData? = null,
) {

    @Bean
    open fun producerConfig() = mutableMapOf<String, Any>().also {
        it[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaConfig?.bootstrapServers!!
        it[kafkaConfig.schemaRegistryUrlKey!!] = kafkaConfig.schemaRegistryUrl!!
        it[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] =
            kafkaProducerConfigData?.keySerializerClass!!
        it[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
            kafkaProducerConfigData.valueSerializerClass
        it[ProducerConfig.BATCH_SIZE_CONFIG] = kafkaProducerConfigData.batchSize *
            kafkaProducerConfigData.batchSizeBoostFactor
        it[ProducerConfig.LINGER_MS_CONFIG] = kafkaProducerConfigData.lingerMs
        it[ProducerConfig.COMPRESSION_TYPE_CONFIG] = kafkaProducerConfigData.compressionType
        it[ProducerConfig.ACKS_CONFIG] = kafkaProducerConfigData.acks
        it[ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG] = kafkaProducerConfigData.requestTimeoutMs
        it[ProducerConfig.RETRIES_CONFIG] = kafkaProducerConfigData.retryCount
    }

    @Bean
    open fun producerFactory(): ProducerFactory<K, V> = DefaultKafkaProducerFactory(producerConfig())

    @Bean
    open fun kafkaTemplate(): KafkaTemplate<K, V> = KafkaTemplate(producerFactory())
}
