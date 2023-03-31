package co.cp.orderly.order.domain.application.service.integraiton

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderAddressDTO
import co.cp.orderly.order.domain.application.service.dto.internal.order.OrderItemDTO
import co.cp.orderly.order.domain.application.service.integraiton.mocks.Mocks
import co.cp.orderly.order.domain.application.service.ports.input.service.OrderApplicationService
import co.cp.orderly.order.domain.application.service.ports.output.repository.CustomerRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.ShopRepository
import co.cp.orderly.order.domain.application.service.utils.MockitoHelper
import co.cp.orderly.order.domain.application.service.utils.dataMapper.OrderApplicationServiceDataMapper
import co.cp.orderly.order.domain.core.entity.Customer
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.Product
import co.cp.orderly.order.domain.core.entity.Shop
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.UUID

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [Mocks::class])
open class OrderApplicationServiceTest {

    private lateinit var createOrderCommandDTO: CreateOrderCommandDTO
    private lateinit var createOrderCommandDTOWrongPrice: CreateOrderCommandDTO
    private lateinit var createOrderCommandDTOWrongProductPrice: CreateOrderCommandDTO

    @Autowired private lateinit var orderApplicationService: OrderApplicationService

    @Autowired private lateinit var shopRepository: ShopRepository

    @Autowired private lateinit var customerRepository: CustomerRepository

    @Autowired private lateinit var orderRepository: OrderRepository

    @Autowired private lateinit var orderApplicationServiceDataMapper: OrderApplicationServiceDataMapper

    private val customerId = UUID.fromString("36418fe4-efc1-45c1-a013-51be6af1bb0e")
    private val shopId = UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")
    private val productId = UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")
    private val productId2nd = UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")
    private val orderId = UUID.fromString("0ff9538f-26b5-4602-b847-1e06e970c6a9")
    private val price = BigDecimal("650.00")
    private val address = Triple("Streetexample 35A", "3242", "Berlin")

    @BeforeAll
    open fun init() {
        createOrderCommandDTO = CreateOrderCommandDTO(
            customerId,
            shopId,
            price,
            listOf(
                OrderItemDTO(productId, 6, BigDecimal("100.00"), BigDecimal("600.00")),
                OrderItemDTO(productId, 1, BigDecimal("50.00"), BigDecimal("50.00")),
            ),
            OrderAddressDTO(address.first, address.second, address.third)
        )

        createOrderCommandDTOWrongPrice = CreateOrderCommandDTO(
            customerId,
            shopId,
            BigDecimal("713.00"),
            listOf(
                OrderItemDTO(productId, 6, BigDecimal("89.00"), BigDecimal("534.00")),
                OrderItemDTO(productId, 3, BigDecimal("50.00"), BigDecimal("150.00")),
            ),
            OrderAddressDTO(address.first, address.second, address.third)
        )

        createOrderCommandDTOWrongProductPrice = CreateOrderCommandDTO(
            customerId,
            shopId,
            BigDecimal("210.00"),
            listOf(
                OrderItemDTO(productId, 1, BigDecimal("61.00"), BigDecimal("60.00")),
                OrderItemDTO(productId, 3, BigDecimal("50.00"), BigDecimal("150.00")),
            ),
            OrderAddressDTO(address.first, address.second, address.third)
        )

        val customer = Customer()
        customer.setId(CustomerId(customerId))

        val shop = Shop.builder()
            .shopId(ShopId(createOrderCommandDTO.shopId))
            .products(
                listOf(
                    Product(ProductId(productId), "Tomatoes", Money(BigDecimal("50.00"))),
                    Product(ProductId(productId), "Bananas", Money(BigDecimal("50.00")))
                )
            )
            .active(true)
            .build()

        val order = orderApplicationServiceDataMapper.createOrderCommandToOrder(createOrderCommandDTO)
        order.setId(OrderId(orderId))

        Mockito.`when`(customerRepository.findCustomerById(customerId)).thenReturn(customer)

        Mockito.`when`(
            shopRepository.getShopDetails(
                orderApplicationServiceDataMapper.createOrderCommandToShop(createOrderCommandDTO)
            )
        ).thenReturn(shop)

        Mockito.`when`(orderRepository.saveOrder(MockitoHelper.any(Order::class.java))).thenReturn(order)
    }

    @Test
    fun `Should create an Order`() {
        val createOrderResponse = orderApplicationService.createOrder(createOrderCommandDTO)

        assertEquals(OrderStatus.PENDING, createOrderResponse.orderStatus)
        assertEquals("Order has been successfully created", createOrderResponse.message)
        assertNotNull(createOrderResponse.orderTrackingId)
    }

    @Test
    fun `Should throw an exception when creating an Order with a wrong total price`() {

        val exceptionData = assertThrows<OrderDomainException> {
            orderApplicationService.createOrder(createOrderCommandDTOWrongPrice)
        }
        assertEquals(
            "Total Price: 713.00 doesn't match the order items total price 684.00",
            exceptionData.message
        )
    }

    @Test
    fun `Should throw an exception when creating an Order with a wrong product price`() {

        val exceptionData = assertThrows<OrderDomainException> {
            orderApplicationService.createOrder(createOrderCommandDTOWrongProductPrice)
        }

        assertEquals(
            exceptionData.message,
            "Order item price: 61.00 is not valid for product: 441c16fd-91de-4089-85c3-2ea388d319db"
        )
    }

    @Test
    fun `should throw an exception when trying to create an order from an inactive shop`() {
        val shopResponse = Shop.builder()
            .shopId(ShopId(createOrderCommandDTO.shopId))
            .products(
                listOf(
                    Product(ProductId(productId), "product-1", Money(BigDecimal("50.00"))),
                    Product(ProductId(productId), "product-2", Money(BigDecimal("50.00")))
                )
            ).active(false)

        Mockito.`when`(
            shopRepository.getShopDetails(orderApplicationServiceDataMapper.createOrderCommandToShop(createOrderCommandDTO))
        )
            .thenReturn(shopResponse.build())

        val orderDomainException = assertThrows<OrderDomainException> {
            orderApplicationService.createOrder(createOrderCommandDTO)
        }
        assertEquals(orderDomainException.message, "Shop #$shopId is currently inactive")

        Mockito.`when`(
            shopRepository.getShopDetails(orderApplicationServiceDataMapper.createOrderCommandToShop(createOrderCommandDTO))
        )
            .thenReturn(shopResponse.active(true).build())
    }
}
