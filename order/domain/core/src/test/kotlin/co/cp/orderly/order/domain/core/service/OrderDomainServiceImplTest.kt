package co.cp.orderly.order.domain.core.service

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.OrderItem
import co.cp.orderly.order.domain.core.entity.Product
import co.cp.orderly.order.domain.core.entity.Shop
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import co.cp.orderly.order.domain.core.vos.OrderItemId
import co.cp.orderly.order.domain.core.vos.StreetAddress
import co.cp.orderly.order.domain.core.vos.TrackingId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.UUID

class OrderDomainServiceImplTest {

    val order = Order.builder()
        .customerId(CustomerId(UUID.randomUUID()))
        .shopId(ShopId(UUID.randomUUID()))
        .deliveryAddress(StreetAddress(UUID.randomUUID(), "Street name 20", "5431", "Berlin"))
        .price(Money(BigDecimal(10714.00)))
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
                        ),
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
                            "Bananas",
                            Money(BigDecimal(32))
                        )
                    )
                    .quantity(1)
                    .price(Money(BigDecimal(32.00)))
                    .subTotal(Money(BigDecimal(32.00)))
                    .build(),

                OrderItem.builder()
                    .orderItemId(OrderItemId(3124253676))
                    .orderId(OrderId(UUID.randomUUID()))
                    .product(
                        Product(
                            ProductId(UUID.fromString("4fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
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
        .errorMessages(mutableListOf("sfgdfg", "rwagsdfdg", "fsgdfd"))

    val shop = Shop.Builder(
        ShopId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")),
        listOf(
            Product(
                ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                "Tomatoes",
                Money(BigDecimal(5325.00))
            ),
            Product(
                ProductId(UUID.fromString("6fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                "Bananas",
                Money(BigDecimal(32))
            ),
            Product(
                ProductId(UUID.fromString("8fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                "Apples",
                Money(BigDecimal(3435))
            ),
            Product(
                ProductId(UUID.fromString("7fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                "Oranges",
                Money(BigDecimal(32))
            ),
        ),
        true
    )

    @Test
    fun `should validate a shop object using validateShop`() {
        var shop = shop.shopId(
            ShopId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db"))
        ).active(false)
        val shopBuilder1 = shop.build()
        val order = order.build()
        val exceptionData = assertThrows<OrderDomainException> {
            OrderDomainServiceImpl().validateAndStartOrder(order, shopBuilder1)
        }

        assertEquals(
            exceptionData.message,
            "Shop #441c16fd-91de-4089-85c3-2ea388d319db is currently inactive"
        )

        val nullableShop = shop.shopId(
            ShopId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db"))
        ).active(null).build()

        assertEquals(nullableShop.active, null)
    }

    @Test
    fun `it should validate and start an order`() {
        val orderCopy = order.build()
        OrderDomainServiceImpl().validateAndStartOrder(orderCopy, shop.build())
        assertEquals(orderCopy.orderStatus, OrderStatus.PENDING)

        val orderNullable = order.orderId(null).build()
        OrderDomainServiceImpl().validateAndStartOrder(orderNullable, shop.build())
        assertEquals(order.orderId, null)
    }

    @Test
    fun `it should pay an order`() {
        val orderO = order.orderId(OrderId(UUID.randomUUID())).orderStatus(OrderStatus.PENDING).build()
        OrderDomainServiceImpl().payOrder(orderO)
        assertEquals(orderO.orderStatus, OrderStatus.PAID)
    }

    @Test
    fun `it should approve an order`() {
        val orderO = order.orderId(OrderId(UUID.randomUUID())).orderStatus(OrderStatus.PAID).build()
        OrderDomainServiceImpl().approveOrder(orderO)
        assertEquals(orderO.orderStatus, OrderStatus.APPROVED)
    }

    @Test
    fun `it should cancel an order's payment`() {
        val orderO = order
            .orderId(OrderId(UUID.randomUUID()))
            .orderStatus(OrderStatus.PAID)
            .errorMessages(mutableListOf("Error #1"))
            .build()

        orderO.errorMessages?.let { OrderDomainServiceImpl().cancelOrderPayment(orderO, it) }
        assertEquals(orderO.orderStatus, OrderStatus.CANCELLING)
    }

    @Test
    fun `it should cancel an order`() {
        val orderO = order
            .orderId(OrderId(UUID.randomUUID()))
            .orderStatus(OrderStatus.CANCELLING)
            .errorMessages(mutableListOf("Error #1"))
            .build()

        orderO.errorMessages?.let { OrderDomainServiceImpl().cancelOrder(orderO, it) }
        assertEquals(orderO.orderStatus, OrderStatus.CANCELED)
    }
}
