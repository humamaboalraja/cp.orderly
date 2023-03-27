package co.cp.orderly.order.main

import co.cp.orderly.order.domain.core.service.IOrderDomainService
import co.cp.orderly.order.domain.core.service.OrderDomainServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderServiceContainerConfig {

    @Bean
    fun orderDomainService(): IOrderDomainService = OrderDomainServiceImpl()
}
