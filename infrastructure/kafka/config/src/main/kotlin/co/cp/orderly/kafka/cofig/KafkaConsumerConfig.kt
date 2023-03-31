package co.cp.orderly.kafka.cofig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
open class KafkaConsumerConfig {
    lateinit var keyDeserializer: String;
    lateinit var valueDeserializer: String;
    lateinit var autoOffsetReset: String;
    lateinit var specificAvroReaderKey: String;
    lateinit var specificAvroReader: String;
    var batchListener by Delegates.notNull<Boolean>();
    var autoStartup by Delegates.notNull<Boolean>();
    var concurrencyLevel by Delegates.notNull<Int>();
    var sessionTimeoutMs by Delegates.notNull<Int>();
    var heartbeatIntervalMs by Delegates.notNull<Int>();
    var maxPollIntervalMs by Delegates.notNull<Int>();
    var pollTimeoutMs by Delegates.notNull<Long>();
    var maxPollRecords by Delegates.notNull<Int>();
    var maxPartitionFetchBytesDefault by Delegates.notNull<Int>();
    var maxPartitionFetchBytesBoostFactor by Delegates.notNull<Int>();
}
