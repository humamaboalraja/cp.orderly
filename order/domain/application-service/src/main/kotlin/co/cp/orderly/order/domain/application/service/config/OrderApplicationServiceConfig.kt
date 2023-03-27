package co.cp.orderly.order.domain.application.service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "order-service")
open class OrderApplicationServiceConfig(
    var paymentRequestTopicName: String? = null,
    var paymentResponseTopicName: String? = null,
    var shopApprovalRequestTopicName: String? = null,
    var shopApprovalResponseTopicName: String? = null
)
