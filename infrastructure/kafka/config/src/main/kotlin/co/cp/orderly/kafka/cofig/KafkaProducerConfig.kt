package co.cp.orderly.kafka.cofig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kafka-producer-config")
data class KafkaProducerConfigData(
    var keySerializerClass: String? = null,
    var valueSerializerClass: String? = null,
    var compressionType: String? = null,
    var acks: String? = null,
    var batchSize: Int? = null,
    var batchSizeBoostFactor: Int? = null,
    var lingerMs: Int? = null,
    var requestTimeoutMs: Int? = null,
    var retryCount: Int? = null,
)

@Configuration
open class KafkaProducerConfig {
    @Bean open fun data(): KafkaProducerConfigData = KafkaProducerConfigData()
}
