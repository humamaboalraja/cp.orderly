package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import co.cp.orderly.order.domain.core.vos.OrderItemId
import co.cp.orderly.order.domain.core.vos.StreetAddress
import co.cp.orderly.order.domain.core.vos.TrackingId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.UUID

class OrderTest {

    private val order = Order.builder()
        .orderId(OrderId(UUID.randomUUID()))
        .customerId(CustomerId(UUID.randomUUID()))
        .shopId(ShopId(UUID.randomUUID()))
        .deliveryAddress(StreetAddress(UUID.randomUUID(), "Example Stree 12A", "3421", "Berlin"))
        .price(Money(BigDecimal(10682.00)))
        .items(
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "Tomatoes",
                            Money(BigDecimal(5325.00))
                        )
                    )
                    .quantity(2)
                    .price(Money(BigDecimal(5325)))
                    .subTotal(Money(BigDecimal(10650)))
                    .build(),

                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("6fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "Tomatoes",
                            Money(BigDecimal(32))
                        )
                    )
                    .quantity(1)
                    .price(Money(BigDecimal(32.00)))
                    .subTotal(Money(BigDecimal(32.00)))
                    .build(),
            )
        )
        .trackingId(TrackingId(UUID.randomUUID()))
        .orderStatus(OrderStatus.PENDING)
        .failureMessages(mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd"))
        .build()

    private val orderFourth = Order.builder()
        .customerId(CustomerId(UUID.randomUUID()))
        .shopId(ShopId(UUID.randomUUID()))
        .price(Money(BigDecimal(10682.00)))
        .items(
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "Tomatoes",
                            Money(BigDecimal(0))
                        )
                    )
                    .quantity(2)
                    .price(Money(BigDecimal(5325)))
                    .subTotal(Money(BigDecimal(10650)))
                    .build(),

                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("6fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "Tomatoes",
                            Money(BigDecimal(32))
                        )
                    )
                    .quantity(1)
                    .price(Money(BigDecimal(0)))
                    .subTotal(Money(BigDecimal(32.00)))
                    .build(),

            )
        )
        .trackingId(TrackingId(UUID.randomUUID()))
        .failureMessages(mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd"))

    @Test
    fun `should build an Order using Order's builder then check Order's properties state validity `() {
        val orderBuilder = Order.Builder(
            OrderId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3b12c4b73bd")),
            CustomerId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
            ShopId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
            StreetAddress(
                UUID.fromString("6fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6"),
                "Example Stree 12A", "3421", "Berlin"
            ),
            Money(BigDecimal(10682.00)),
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "Tomatoes",
                            Money(BigDecimal(5325.00))
                        )
                    )
                    .quantity(2)
                    .price(Money(BigDecimal(5325)))
                    .subTotal(Money(BigDecimal(10650)))
                    .build(),
            ),
            TrackingId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4b73bd")),
            OrderStatus.PENDING,
            mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd")
        ).build()

        assertEquals(orderBuilder.orderId, OrderId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3b12c4b73bd")))
        assertEquals(orderBuilder.customerId, CustomerId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")))
        assertEquals(orderBuilder.shopId, ShopId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")))
        assertEquals(
            orderBuilder.deliveryAddress,
            StreetAddress(
                UUID.fromString("6fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6"),
                "Example Stree 12A", "3421", "Berlin"
            )
        )
        assertEquals(orderBuilder.price, Money(BigDecimal(10682.00)))
        assertEquals(
            orderBuilder.items,
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "Tomatoes",
                            Money(BigDecimal(5325.00))
                        )
                    )
                    .quantity(2)
                    .price(Money(BigDecimal(5325)))
                    .subTotal(Money(BigDecimal(10650)))
                    .build(),
            )
        )

        orderBuilder.trackingId = TrackingId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd"))
        assertEquals(orderBuilder.trackingId, TrackingId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd")))

        orderBuilder.failureMessages = mutableListOf("Exception #1", "Exception #1")
        assertEquals(orderBuilder.failureMessages, mutableListOf("Exception #1", "Exception #1"))

        orderFourth.orderStatus(OrderStatus.PENDING).build().cancel(null)
        assertEquals(
            orderFourth.failureMessages,
            mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd")
        )

        var orderMessages = Order.builder().orderStatus(OrderStatus.PAID).failureMessages(null)
        orderMessages.build().initCancel(mutableListOf("Exception #1", "Exception #1"))
        assertEquals(
            orderMessages.failureMessages,
            null
        )

        orderMessages = Order.builder().orderStatus(OrderStatus.PENDING).failureMessages(mutableListOf("s"))
        orderMessages.build().cancel(mutableListOf("Exception #1", "Exception #1"))
        assertEquals(
            orderMessages.failureMessages,
            mutableListOf("s", "Exception #1", "Exception #1")
        )

        orderMessages = Order.builder().orderStatus(OrderStatus.PENDING).failureMessages(mutableListOf("Exception #1", "Exception #2"))
        orderMessages.build().cancel(mutableListOf("Exception #3", "Exception #4"))
        assertEquals(
            orderMessages.failureMessages,
            mutableListOf("Exception #1", "Exception #2", "Exception #3", "Exception #4")
        )

        orderFourth.items(null).build().startNewtOrder()
        assertEquals(orderFourth.items, null)

        var order = Order.builder()
        order.orderId = OrderId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd"))
        assertEquals(order.orderId, OrderId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd")))

        order.customerId = CustomerId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd"))
        assertEquals(order.customerId, CustomerId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd")))

        order.shopId = ShopId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd"))
        assertEquals(order.shopId, ShopId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd")))

        order.deliveryAddress = StreetAddress(
            UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd"),
            "Example Street 11", "2343", "Berlin"
        )
        assertEquals(
            order.deliveryAddress,
            StreetAddress(
                UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd"),
                "Example Street 11", "2343", "Berlin"
            )
        )

        order.price = Money(BigDecimal(334))
        assertEquals(order.price, Money(BigDecimal(334)))

        order.items = listOf(OrderItem.builder().build())
        assertEquals(order.items, listOf(OrderItem.builder().build()))

        order.trackingId = TrackingId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd"))
        assertEquals(order.trackingId, TrackingId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd")))

        order.orderStatus = OrderStatus.PENDING
        assertEquals(order.orderStatus, OrderStatus.PENDING)

        order.failureMessages = mutableListOf("Exception #1", "Exception #2", "Exception #2")
        assertEquals(order.failureMessages, mutableListOf("Exception #1", "Exception #2", "Exception #2"))
    }

    @Test
    fun `should change the orderStatus using pay() order state machine method`() {
        order.pay()
        assertEquals(order.orderStatus, OrderStatus.PAID)
        order.orderStatus = OrderStatus.APPROVED
        val payStateException = { order.pay() }
        val payStateExceptionResult = assertThrows<OrderDomainException>(payStateException)
        assertEquals(payStateExceptionResult.message, "Order is not in valid state for a pay operation")
    }

    @Test
    fun `should change the orderStatus using approve() order state machine method`() {
        order.orderStatus = OrderStatus.PAID
        order.approve()
        assertEquals(order.orderStatus, OrderStatus.APPROVED)
        order.orderStatus = OrderStatus.APPROVED
        val approveStateException = { order.approve() }
        val approveStateExceptionResult = assertThrows<OrderDomainException>(approveStateException)
        assertEquals(
            approveStateExceptionResult.message,
            "Order is not in valid state for an approve operation"
        )
    }

    @Test
    fun `should change the orderStatus using initCancel(failureMessages) order state machine method`() {
        order.orderStatus = OrderStatus.PAID
        order.failureMessages?.let { order.initCancel(it) }
        assertEquals(order.orderStatus, OrderStatus.CANCELLING)
        order.orderStatus = OrderStatus.APPROVED
        val initCancelStateExceptionResult = assertThrows<OrderDomainException> {
            order.failureMessages?.let { order.initCancel(it) }
        }
        assertEquals(
            initCancelStateExceptionResult.message,
            "Order is not in a valid state for an initCancel operation"
        )
    }

    @Test
    fun `should change the orderStatus using cancel(failureMessages) order state machine method`() {
        order.orderStatus = OrderStatus.CANCELLING
        order.failureMessages?.let { order.cancel(it) }
        assertEquals(order.orderStatus, OrderStatus.CANCELED)
        order.orderStatus = OrderStatus.PENDING
        order.failureMessages?.let { order.cancel(it) }
        assertEquals(order.orderStatus, OrderStatus.CANCELED)
        order.orderStatus = OrderStatus.APPROVED
        val cancelStateExceptionResult = assertThrows<OrderDomainException> {
            order.failureMessages?.let { order.cancel(it) }
        }
        assertEquals(cancelStateExceptionResult.message, "Order is not in a valid state for a cancel operation")
    }

    @Test
    fun `should call startNewtOrder() and verify the state changes`() {
        order.startNewtOrder()
        assertEquals(order.orderStatus, OrderStatus.PENDING)
        assertDoesNotThrow { UUID.fromString(order.orderId?.getValue().toString()) }
        assertDoesNotThrow { UUID.fromString(order.trackingId?.getValue().toString()) }
    }

    @Test
    fun `should call validateItemPrice() and verify its internal methods call`() {

        Order.builder()
            .items(
                listOf(
                    OrderItem.builder().product(
                        Product(ProductId(UUID.randomUUID()), "asd", Money(BigDecimal(20)))
                    ).build()
                )
            ).price(Money(BigDecimal(20))).build().validateOrder()
    }

    @Test
    fun `should call validateItemsPrice() and verify its internal methods call`() {
        val exceptionData = assertThrows<OrderDomainException> {
            Order.builder().price(Money(BigDecimal(0))).build().validateOrder()
        }
        assertEquals(
            exceptionData.message,
            "The total price must be greater than 0"
        )
        orderFourth.price(Money(BigDecimal(1065790)))
            .items(
                listOf(
                    OrderItem.builder()
                        .product(
                            Product(
                                ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                                "Tomatoes",
                                Money(BigDecimal(53285))
                            )
                        )
                        .quantity(2)
                        .price(Money(BigDecimal(53285)))
                        .subTotal(Money(BigDecimal(106570)))
                        .build()
                )
            )

        var exceptionDataTwo = assertThrows<OrderDomainException> { orderFourth.build().validateOrder() }
        assertEquals(exceptionDataTwo.message, "Total Price: 1065790 doesn't match the order items total price 106570.00")

        exceptionDataTwo = assertThrows<OrderDomainException> { Order.builder().build().validateOrder() }
        assertEquals(exceptionDataTwo.message, "The total price must be greater than 0")
    }

    @Test
    fun `should call validateInitialOrder() and verify its cases`() {
        val exceptionData = assertThrows<OrderDomainException> {
            Order.builder()
                .orderId(OrderId(UUID.randomUUID())).price(Money(BigDecimal(0)))
                .build().validateOrder()
        }
        assertEquals(
            exceptionData.message,
            "Order is not in a correct state for initialization"
        )
    }

    @Test
    fun `should call validateOrder() and verify its internal methods calls & their cases`() {

        var exceptionData = assertThrows<OrderDomainException> { orderFourth.build().validateOrder() }
        assertEquals(exceptionData.message, "Order item price: 5325 is not valid for product: 9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")

        orderFourth.price(null).items(listOf(OrderItem.builder().product(null).build())).build()
        exceptionData = assertThrows { orderFourth.build().validateOrder() }
        assertEquals(exceptionData.message, "The total price must be greater than 0")

        exceptionData = assertThrows { orderFourth.build().validateOrder() }
        assertEquals(exceptionData.message, "The total price must be greater than 0")

        orderFourth.orderStatus(OrderStatus.PENDING).orderId(OrderId(UUID.randomUUID())).build()
        exceptionData = assertThrows { orderFourth.build().validateOrder() }
        assertEquals(exceptionData.message, "Order is not in a correct state for initialization")

        assertDoesNotThrow {
            orderFourth
                .orderStatus(null)
                .orderId(null)
                .price(Money(BigDecimal(106570)))
                .items(
                    listOf(
                        OrderItem.builder()
                            .orderItemId(OrderItemId(3124253676))
                            .orderId(OrderId(UUID.randomUUID()))
                            .product(
                                Product(
                                    ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                                    "Tomatoes",
                                    Money(BigDecimal(53285))
                                )
                            )
                            .quantity(2)
                            .price(Money(BigDecimal(53285)))
                            .subTotal(Money(BigDecimal(106570)))
                            .build()
                    )
                )
                .build().validateOrder()
        }
    }

    @Test
    fun `Should call order's toString() method and verify its returned value`() {
        assertTrue(!order.toString().isNullOrEmpty() && order.toString().startsWith("Order", true))
        listOf(
            "failureMessages", "orderId", "customerId", "shopId", "price", "items", "orderStatus", "failureMessages"
        ).forEach {
            assertTrue(order.toString().contains(it))
        }
    }
}
