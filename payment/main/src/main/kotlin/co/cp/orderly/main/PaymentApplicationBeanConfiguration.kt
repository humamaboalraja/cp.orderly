package co.cp.orderly.main

import co.cp.orderly.payment.domain.core.IPaymentDomainService
import co.cp.orderly.payment.domain.core.service.PaymentDomainServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class PaymentApplicationBeanConfiguration {

    @Bean
    open fun paymentDomainService(): IPaymentDomainService = PaymentDomainServiceImpl()
}
