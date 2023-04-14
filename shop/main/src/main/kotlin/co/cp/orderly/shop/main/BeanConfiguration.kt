package co.cp.orderly.shop.main

import co.cp.orderly.shop.domain.core.service.IShopDomainService
import co.cp.orderly.shop.domain.core.service.ShopDomainServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class BeanConfiguration {
    @Bean
    open fun shopDomainService(): IShopDomainService = ShopDomainServiceImpl()
}
