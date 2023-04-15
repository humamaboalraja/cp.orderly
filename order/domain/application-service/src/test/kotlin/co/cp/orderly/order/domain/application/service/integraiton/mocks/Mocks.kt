package co.cp.orderly.order.domain.application.service.integraiton.mocks

import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment.PaymentRequestMessagePublisher
import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.shop.ShopApprovalRequestMessagePublisher
import co.cp.orderly.order.domain.application.service.ports.output.repository.ApprovalConsistencyRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.CustomerRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.PaymentConsistencyRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.ShopRepository
import co.cp.orderly.order.domain.core.service.IOrderDomainService
import co.cp.orderly.order.domain.core.service.OrderDomainServiceImpl
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackages = ["co.cp.orderly"])
open class Mocks {

    @Bean
    open fun paymentRequestMessagePublisher(): PaymentRequestMessagePublisher? =
        Mockito.mock(PaymentRequestMessagePublisher::class.java)

    @Bean
    open fun shopApprovalRequestMessagePublisher(): ShopApprovalRequestMessagePublisher =
        Mockito.mock(ShopApprovalRequestMessagePublisher::class.java)

    @Bean open fun orderRepository(): OrderRepository = Mockito.mock(OrderRepository::class.java)

    @Bean open fun customerRepository(): CustomerRepository = Mockito.mock(CustomerRepository::class.java)

    @Bean open fun shopRepository(): ShopRepository = Mockito.mock(ShopRepository::class.java)

    @Bean
    open fun paymentConsistencyRepository(): PaymentConsistencyRepository? =
        Mockito.mock(PaymentConsistencyRepository::class.java)

    @Bean
    open fun approvalConsistencyRepository(): ApprovalConsistencyRepository? =
        Mockito.mock(ApprovalConsistencyRepository::class.java)

    @Bean open fun orderDomainService(): IOrderDomainService = OrderDomainServiceImpl()
}
