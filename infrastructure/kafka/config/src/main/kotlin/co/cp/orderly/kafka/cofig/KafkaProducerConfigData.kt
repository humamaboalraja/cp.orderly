package co.cp.orderly.kafka.cofig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
open class KafkaProducerConfig {
    lateinit var keySerializerClass: String
    lateinit var valueSerializerClass: String
    lateinit var compressionType: String
    lateinit var acks: String
    lateinit var batchSize: Int
    lateinit var batchSizeBoostFactor: Int
    lateinit var lingerMs: Int
    lateinit var requestTimeoutMs: Int
    lateinit var retryCount: Int
}
