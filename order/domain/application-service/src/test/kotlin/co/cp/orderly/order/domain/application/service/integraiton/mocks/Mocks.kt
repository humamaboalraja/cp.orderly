package co.cp.orderly.order.domain.application.service.integraiton.mocks

import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher
import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.shop.OrderPaidShopRequestMessagePublisher
import co.cp.orderly.order.domain.application.service.ports.output.repository.CustomerRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.ShopRepository
import co.cp.orderly.order.domain.core.service.IOrderDomainService
import co.cp.orderly.order.domain.core.service.OrderDomainServiceImpl
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackages = ["co.cp.orderly.order.domain"])
open class Mocks {

    @Bean
    open fun orderCreatedPaymentRequestMessagePublisher(): OrderCreatedPaymentRequestMessagePublisher {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher::class.java)
    }

    @Bean
    open fun orderCancelledPaymentRequestMessagePublisher(): OrderCancelledPaymentRequestMessagePublisher =
        Mockito.mock(OrderCancelledPaymentRequestMessagePublisher::class.java)

    @Bean
    open fun orderPaidShopRequestMessagePublisher(): OrderPaidShopRequestMessagePublisher =
        Mockito.mock(OrderPaidShopRequestMessagePublisher::class.java)

    @Bean open fun orderRepository(): OrderRepository = Mockito.mock(OrderRepository::class.java)

    @Bean open fun customerRepository(): CustomerRepository = Mockito.mock(CustomerRepository::class.java)

    @Bean open fun shopRepository(): ShopRepository = Mockito.mock(ShopRepository::class.java)

    @Bean
    open fun orderDomainService(): IOrderDomainService = OrderDomainServiceImpl()
}
