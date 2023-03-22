package co.cp.orderly.kafka.cofig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kafka-config")
data class KafkaConfigData(
    var bootstrapServers: String? = null,
    var schemaRegistryUrlKey: String? = null,
    var schemaRegistryUrl: String? = null,
    var partitionsNumber: Int? = null,
    var replicationFactor: Short? = null
)

@Configuration
open class KafkaConfig { @Bean open fun data(): KafkaConfigData = KafkaConfigData() }
