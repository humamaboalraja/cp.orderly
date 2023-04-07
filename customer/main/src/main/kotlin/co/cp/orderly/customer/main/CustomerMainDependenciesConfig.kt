package co.cp.orderly.customer.main

import co.cp.orderly.customer.domain.core.service.CustomerDomainServiceImpl
import co.cp.orderly.customer.domain.core.service.ICustomerCoreDomainService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomerMainDependenciesConfig {
    @Bean
    fun customerDomainService(): ICustomerCoreDomainService? = CustomerDomainServiceImpl()

}
