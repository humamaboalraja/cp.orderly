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
open class ConsumerConfigKafka<K : Serializable, V : SpecificRecordBase>(
    private val kafkaConfig: KafkaConfig,
    private val kafkaConsumerConfig: KafkaConsumerConfig,
) {

    @Bean
    open fun consumerConfigs(): Map<String, Any> =
        mapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaConfig.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to kafkaConsumerConfig.keyDeserializer,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to kafkaConsumerConfig.valueDeserializer,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to kafkaConsumerConfig.autoOffsetReset,
            kafkaConfig.schemaRegistryUrlKey to kafkaConfig.schemaRegistryUrl,
            kafkaConsumerConfig.specificAvroReaderKey to kafkaConsumerConfig.specificAvroReader,
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to kafkaConsumerConfig.sessionTimeoutMs,
            ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG to kafkaConsumerConfig.heartbeatIntervalMs,
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to kafkaConsumerConfig.maxPollIntervalMs,
            ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG
                to kafkaConsumerConfig.maxPartitionFetchBytesDefault *
                    kafkaConsumerConfig.maxPartitionFetchBytesBoostFactor,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to kafkaConsumerConfig.maxPollRecords,
        )

    @Bean
    open fun consumerFactory(): ConsumerFactory<K, V> = DefaultKafkaConsumerFactory(consumerConfigs())

    @Bean
    open fun kafkaListenerContainerFactory():
        KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> =
        ConcurrentKafkaListenerContainerFactory<K, V>().also {
            it.consumerFactory = consumerFactory()
            it.isBatchListener = kafkaConsumerConfig.batchListener
            it.setConcurrency(kafkaConsumerConfig.concurrencyLevel)
            it.setAutoStartup(kafkaConsumerConfig.autoStartup)
            it.containerProperties.pollTimeout = kafkaConsumerConfig.pollTimeoutMs
        }
}
