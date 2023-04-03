package co.cp.orderly.shop.application.service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(value = "shop-service")
open class ShopServiceConfigData {
    lateinit var shopApprovalRequestTopicName: String
    lateinit var shopApprovalResponseTopicName: String
}
