package co.cp.orderly.kafka.cofig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka-config")
open class KafkaConfig(
    var bootstrapServers: String? = null,
    var schemaRegistryUrlKey: String? = null,
    var schemaRegistryUrl: String? = null,
    var partitionsNumber: Int? = null,
    var replicationFactor: Short? = null
)
