package cp.cp.orderly.customer.domain.application.service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "customer-service")
open class CustomerApplicationServiceConfig {
    lateinit var customerTopicName: String
}
