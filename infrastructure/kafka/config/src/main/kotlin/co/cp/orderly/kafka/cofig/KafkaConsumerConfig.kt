package co.cp.orderly.kafka.cofig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kafka-consumer-config")
data class KafkaConsumerConfigData(
    var keyDeserializer: String? = null,
    var valueDeserializer: String? = null,
    var autoOffsetReset: String? = null,
    var specificAvroReaderKey: String? = null,
    var specificAvroReader: String? = null,
    var batchListener: Boolean? = null,
    var autoStartup: Boolean? = null,
    var concurrencyLevel: Int? = null,
    var sessionTimeoutMs: Int? = null,
    var heartbeatIntervalMs: Int? = null,
    var maxPollIntervalMs: Int? = null,
    var pollTimeoutMs: Long? = null,
    var maxPollRecords: Int? = null,
    var maxPartitionFetchBytesDefault: Int? = null,
    var maxPartitionFetchBytesBoostFactor: Int? = null,
)

@Configuration
open class KafkaConsumerConfig { @Bean open fun data(): KafkaConsumerConfigData = KafkaConsumerConfigData() }
