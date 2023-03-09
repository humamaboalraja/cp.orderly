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


}
