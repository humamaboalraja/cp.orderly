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
import org.junit.jupiter.api.Assertions.assertNotEquals
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
        .deliveryAddress(StreetAddress(UUID.randomUUID(), "asfdg", "fsgdfg", "dsfgf"))
        .price(Money(BigDecimal(10682.00)))
        .items(
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "dsfgnh",
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
                            "dsfgnh",
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

    private val orderSecond = Order.builder()
        .customerId(CustomerId(UUID.randomUUID()))
        .shopId(ShopId(UUID.randomUUID()))
        .deliveryAddress(StreetAddress(UUID.randomUUID(), "asfdg", "fsgdfg", "dsfgf"))
        .price(Money(BigDecimal(0)))
        .items(
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "dsfgnh",
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
                            "dsfgnh",
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
        .failureMessages(mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd"))
        .build()

    private val orderThird = Order.builder()
        .orderId(OrderId(UUID.randomUUID()))
        .customerId(CustomerId(UUID.randomUUID()))
        .shopId(ShopId(UUID.randomUUID()))
        .deliveryAddress(StreetAddress(UUID.randomUUID(), "asfdg", "fsgdfg", "dsfgf"))
        .price(Money(BigDecimal(0)))
        .items(
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "dsfgnh",
                            Money(BigDecimal(0))
                        )
                    )
                    .quantity(2)
                    .price(Money(BigDecimal(0)))
                    .subTotal(Money(BigDecimal(0)))
                    .build(),

                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("6fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "dsfgnh",
                            Money(BigDecimal(0))
                        )
                    )
                    .quantity(1)
                    .price(Money(BigDecimal(0)))
                    .subTotal(Money(BigDecimal(0)))
                    .build(),
            ),
        )
        .trackingId(TrackingId(UUID.randomUUID()))
        .failureMessages(mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd"))
        .build()

    private val orderFourth = Order.builder()
        .customerId(CustomerId(UUID.randomUUID()))
        .shopId(ShopId(UUID.randomUUID()))
        .deliveryAddress(StreetAddress(UUID.randomUUID(), "asfdg", "fsgdfg", "dsfgf"))
        .price(Money(BigDecimal(10682.00)))
        .items(
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "dsfgnh",
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
                            "dsfgnh",
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
    fun `should `() {
        val d = Order.Builder(
            OrderId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3b12c4b73bd")),
            CustomerId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
            ShopId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
            StreetAddress(
                UUID.fromString("6fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6"),
                "asfdg", "fsgdfg", "dsfgf"
            ),
            Money(BigDecimal(10682.00)),
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "dsfgnh",
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

        assertEquals(d.orderId, OrderId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3b12c4b73bd")))
        assertEquals(d.customerId, CustomerId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")))
        assertEquals(d.shopId, ShopId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")))
        assertEquals(
            d.deliveryAddress,
            StreetAddress(
                UUID.fromString("6fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6"), "asfdg", "fsgdfg", "dsfgf"
            )
        )
        assertEquals(d.price, Money(BigDecimal(10682.00)))
        assertEquals(
            d.items,
            listOf(
                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                            "dsfgnh",
                            Money(BigDecimal(5325.00))
                        )
                    )
                    .quantity(2)
                    .price(Money(BigDecimal(5325)))
                    .subTotal(Money(BigDecimal(10650)))
                    .build(),
            )
        )

        assertEquals(d.trackingId, TrackingId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4b73bd")))
        assertNotEquals(d.trackingId, TrackingId(UUID.fromString("ac1d36e8-fef4-4d19-8881-d3c12c4a73bd")))
        assertEquals(d.orderStatus, OrderStatus.PENDING)
        assertEquals(d.failureMessages, mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd"))

        val dNull = Order.Builder(
            null, null, null, null, null,
            null, null, null, mutableListOf()
        )
        assertEquals(
            dNull,
            Order.Builder(
                orderId = null, customerId = null, shopId = null, deliveryAddress = null,
                price = null, items = null, trackingId = null, orderStatus = null, failureMessages = mutableListOf()
            )
        )
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
        order.initCancel(order.failureMessages)
        assertEquals(order.orderStatus, OrderStatus.CANCELLING)
        order.orderStatus = OrderStatus.APPROVED
        val initCancelStateException = { order.initCancel(order.failureMessages) }
        val initCancelStateExceptionResult = assertThrows<OrderDomainException>(initCancelStateException)
        assertEquals(
            initCancelStateExceptionResult.message,
            "Order is not in a valid state for an initCancel operation"
        )
    }

    @Test
    fun `should change the orderStatus using cancel(failureMessages) order state machine method`() {
        order.orderStatus = OrderStatus.CANCELLING
        order.cancel(order.failureMessages)
        assertEquals(order.orderStatus, OrderStatus.CANCELED)
        order.orderStatus = OrderStatus.PENDING
        order.cancel(order.failureMessages)
        assertEquals(order.orderStatus, OrderStatus.CANCELED)
        order.orderStatus = OrderStatus.APPROVED
        val cancelStateException = { order.cancel(order.failureMessages) }
        val cancelStateExceptionResult = assertThrows<OrderDomainException>(cancelStateException)
        assertEquals(
            cancelStateExceptionResult.message,
            "Order is not in a valid state for a cancel operation"
        )
    }

    @Test
    fun `should call startNewtOrder() and verify the state changes`() {
        order.startNewtOrder()
        assertEquals(order.orderStatus, OrderStatus.PENDING)
        assertDoesNotThrow { UUID.fromString(order.orderId?.getValue().toString()) }
        assertDoesNotThrow { UUID.fromString(order.trackingId?.getValue().toString()) }
    }

    @Test
    fun `should call validateItemsPrice() and verify its internal methods call`() {
        val exceptionData = assertThrows<OrderDomainException> { orderSecond.validateOrder() }
        assertEquals(
            exceptionData.message,
            "The total price must be greater than 0"
        )
    }

    @Test
    fun `should call validateInitialOrder() and verify its cases`() {
        val exceptionData = assertThrows<OrderDomainException> { orderThird.validateOrder() }
        assertEquals(
            exceptionData.message,
            "Order is not in a correct state for initialization"
        )
    }

    @Test
    fun `should call validateOrder() and verify its internal methods calls & their cases`() {

        assertDoesNotThrow { }
        var exceptionData = assertThrows<OrderDomainException> { orderFourth.build().validateOrder() }
        exceptionData.message?.contains("Order item price: 5325 is not valid for product: ")?.let { assertTrue(it) }
        orderFourth.price(null).build()
        exceptionData = assertThrows { orderFourth.build().validateOrder() }
        assertEquals(exceptionData.message, "The total price must be greater than 0")

        orderFourth.orderStatus(OrderStatus.PENDING).orderId(OrderId(UUID.randomUUID())).build()
        exceptionData = assertThrows { orderFourth.build().validateOrder() }
        assertEquals(exceptionData.message, "Order is not in a correct state for initialization")

        exceptionData = assertThrows {
            orderFourth
                .orderStatus(null)
                .orderId(null)
                .price(Money(BigDecimal(9)))
                .items(
                    listOf(
                        OrderItem.builder()
                            .orderItemId(OrderItemId(3124253676))
                            .orderId(OrderId(UUID.randomUUID()))
                            .product(
                                Product(
                                    ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                                    "dsfgnh",
                                    Money(BigDecimal(53285))
                                )
                            )
                            .quantity(8)
                            .price(Money(BigDecimal(5325)))
                            .subTotal(Money(BigDecimal(108650)))
                            .build()
                    )
                )
                .build().validateOrder()
        }
        assertEquals(
            exceptionData.message,
            "Order item price: 5325 is not valid for product: 9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6"
        )

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
                                    "dsfgnh",
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

        val orderFourthObject = orderFourth
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
                                "dsfgnh",
                                Money(BigDecimal(53285))
                            )
                        )
                        .quantity(2)
                        .price(Money(BigDecimal(53285)))
                        .subTotal(Money(BigDecimal(106570)))
                        .build()
                )
            ).failureMessages(mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd"))
            .orderStatus(OrderStatus.PENDING)
        orderFourthObject.build().cancel(mutableListOf("exception", "exception #1", "exception #2"))

        assertEquals(
            orderFourthObject.failureMessages,
            mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd", "exception", "exception #1", "exception #2")
        )

        val orderFourthO2ndObject = orderFourth

        orderFourthO2ndObject.failureMessages(null).build().cancel(mutableListOf("exception", "exception #1", "exception #2"))
        assertEquals(orderFourthO2ndObject.failureMessages, mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd", "exception", "exception #1", "exception #2", "exception", "exception #1", "exception #2"))

        val orderFourthO3rdObject = orderFourth
        orderFourthO3rdObject.items(null).build().startNewtOrder()
        assertEquals(orderFourthO3rdObject.items, null)

        var order5th = orderFourth

        order5th.price(Money(BigDecimal(1065790)))
            .items(
                listOf(
                    OrderItem.builder()
                        .orderItemId(OrderItemId(3124253676))
                        .orderId(OrderId(UUID.randomUUID()))
                        .product(
                            Product(
                                ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                                "dsfgnh",
                                Money(BigDecimal(53285))
                            )
                        )
                        .quantity(2)
                        .price(Money(BigDecimal(53285)))
                        .subTotal(Money(BigDecimal(106570)))
                        .build()
                )
            )

        val exceptionDataTwo = assertThrows<OrderDomainException> { order5th.build().validateItemsPrice() }
        assertEquals(exceptionDataTwo.message, "Total Price: 1065790 doesn't match the order items total price 106570.00")
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
