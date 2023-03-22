package co.cp.orderly.kafka.model.consumer.config

import co.cp.orderly.kafka.cofig.KafkaConfig
import co.cp.orderly.kafka.cofig.KafkaConsumerConfig
import org.apache.avro.specific.SpecificRecordBase
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import java.io.Serializable

@Configuration
open class ConsumerConfig<K : Serializable, V : SpecificRecordBase>(
    private val kafkaConfig: KafkaConfig = KafkaConfig(),
    private val kafkaConsumerConfig: KafkaConsumerConfig = KafkaConsumerConfig(),
) {

    @Bean
    open fun consumerConfigs(): Map<String, Any> =
        mutableMapOf<String, Any>().also {
            val kafkaConsumerConfig = kafkaConsumerConfig
            it[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaConfig.data().bootstrapServers!!
            it[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] =
                kafkaConsumerConfig.data().keyDeserializer!!
            it[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] =
                kafkaConsumerConfig.data().valueDeserializer!!
            it[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] =
                kafkaConsumerConfig.data().autoOffsetReset!!
            it[kafkaConfig.data().schemaRegistryUrlKey!!] =
                kafkaConfig.data().schemaRegistryUrl!!
            it[kafkaConsumerConfig.data().specificAvroReaderKey!!] =
                kafkaConsumerConfig.data().specificAvroReader!!
            it[ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG] =
                kafkaConsumerConfig.data().sessionTimeoutMs!!
            it[ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG] =
                kafkaConsumerConfig.data().heartbeatIntervalMs!!
            it[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] =
                kafkaConsumerConfig.data().maxPollIntervalMs!!
            it[ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG] =
                kafkaConsumerConfig.data().maxPartitionFetchBytesDefault!! *
                kafkaConsumerConfig.data().maxPartitionFetchBytesBoostFactor!!
            it[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] =
                kafkaConsumerConfig.data().maxPollRecords!!
        }

    @Bean
    open fun consumerFactory(): ConsumerFactory<K, V> =
        DefaultKafkaConsumerFactory(consumerConfigs())

    @Bean
    open fun kafkaListenerContainerFactory():
        KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> =
        ConcurrentKafkaListenerContainerFactory<K, V>().also {
            it.consumerFactory = consumerFactory()
            it.isBatchListener = kafkaConsumerConfig.data().batchListener!!
            it.setConcurrency(kafkaConsumerConfig.data().concurrencyLevel!!)
            it.setAutoStartup(kafkaConsumerConfig.data().autoStartup!!)
            it.containerProperties.pollTimeout = kafkaConsumerConfig.data().pollTimeoutMs!!
        }
}
