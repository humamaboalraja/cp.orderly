package co.cp.orderly.payment.domain.service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "payment-service")
open class PaymentApplicationServiceConfigData {
    lateinit var paymentRequestTopicName: String
    lateinit var paymentResponseTopicName: String
}
