package co.cp.orderly.kafka.cofig

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties(prefix = "kafka-config")
open class KafkaConfig {
    lateinit var bootstrapServers: String
    lateinit var schemaRegistryUrlKey: String
    lateinit var schemaRegistryUrl: String
    var partitionsNumber by Delegates.notNull<Int>()
    var replicationFactor by Delegates.notNull<Short>()}
