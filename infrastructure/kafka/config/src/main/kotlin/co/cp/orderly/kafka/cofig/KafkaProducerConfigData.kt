package co.cp.orderly.kafka.cofig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
open class KafkaProducerConfigData {
    lateinit var keySerializerClass: String
    lateinit var valueSerializerClass: String
    lateinit var compressionType: String
    lateinit var acks: String
    var batchSize by Delegates.notNull<Int>()
    var batchSizeBoostFactor by Delegates.notNull<Int>()
    var lingerMs by Delegates.notNull<Int>()
    var requestTimeoutMs by Delegates.notNull<Int>()
    var retryCount by Delegates.notNull<Int>()
}
