package co.cp.orderly.order.domain.application.service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "order-service")
open class OrderApplicationServiceConfig {
    lateinit var paymentRequestTopicName: String
    lateinit var paymentResponseTopicName: String
    lateinit var shopApprovalRequestTopicName: String
    lateinit var shopApprovalResponseTopicName: String
}
