package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.order.domain.core.vos.OrderItemId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class OrderItemTest {
    private val orderItem = OrderItem.builder()
        .orderItemId(OrderItemId(3124253676))
        .orderId(OrderId(UUID.fromString("ab4ffa49-bcdc-4a03-a20b-3ae611a4bffc")))
        .product(
            Product(
                ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                "Bananas",
                Money(BigDecimal(53285))
            )
        )
        .quantity(2)
        .price(Money(BigDecimal(53285)))
        .subTotal(Money(BigDecimal(106570)))

    @Test
    fun `should call and construct OrderItem and its parent using OrderItem's builder`() {

        assertEquals(orderItem.build().orderItemId, OrderItemId(3124253676))

        assertEquals(
            orderItem.build().toString(),
            "OrderItem(orderItemId=BaseId(value=3124253676), " +
                "orderId=BaseId(value=ab4ffa49-bcdc-4a03-a20b-3ae611a4bffc), " +
                "product=BaseEntity(id=BaseId(value=9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6)), " +
                "quantity=2, price=Money(amount=53285), " +
                "subTotal=Money(amount=106570))"
        )
    }

    @Test
    fun `should construct OrderItem fields and check their validity`() {
        val orderItemBuilder = OrderItem.Builder()

        orderItemBuilder.orderItemId = OrderItemId(3435)
        orderItemBuilder.orderId = OrderId(UUID.fromString("ab4ffa49-bcdc-4a03-a20b-3ae611a4bffc"))
        assertEquals(OrderItem.builder().orderId, null)
        orderItemBuilder.product = Product(
            ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
            "Bananas",
            Money(BigDecimal(53285))
        )
        orderItemBuilder.price = Money(BigDecimal(53285))
        orderItemBuilder.quantity = 2
        orderItemBuilder.subTotal = Money(BigDecimal(106570))

        assertEquals(orderItemBuilder.orderItemId, OrderItemId(3435))
        assertEquals(orderItemBuilder.orderId, OrderId(UUID.fromString("ab4ffa49-bcdc-4a03-a20b-3ae611a4bffc")))
        assertEquals(
            orderItemBuilder.product,
            Product(
                ProductId(UUID.fromString("9fb3673d-2bfb-4e1b-a0b7-a0f2bd7098e6")),
                "Bananas",
                Money(BigDecimal(53285))
            )
        )
        assertEquals(orderItemBuilder.price, Money(BigDecimal(53285)))
        assertEquals(orderItemBuilder.quantity, 2)
        assertEquals(orderItemBuilder.subTotal, Money(BigDecimal(106570)))
        assertEquals(OrderItem.Builder(null,).build(), OrderItem.builder().orderItemId(null).build())
    }

    @Test
    fun `should call isPriceValid() and verify its conditional branches`() {
        assertFalse(orderItem.price(null).build().isPriceValid())
        assertEquals(orderItem.price(Money(BigDecimal(0))).build().isPriceValid(), false)

        assertFalse(orderItem.quantity(null).build().isPriceValid())

        assertFalse(
            OrderItem.builder()
                .quantity(2)
                .price(Money(BigDecimal(5385)))
                .subTotal(Money(BigDecimal(10670)))
                .build()
                .isPriceValid()
        )

        assertFalse(
            OrderItem.builder()
                .product(Product(ProductId(UUID.randomUUID()), "Apples", Money(BigDecimal(5385))))
                .quantity(2)
                .price(Money(BigDecimal(5385)))
                .subTotal(Money(BigDecimal(1330670)))
                .build()
                .isPriceValid()
        )

        assertFalse(
            OrderItem.builder()
                .product(Product(ProductId(UUID.randomUUID()), "Apples", Money(BigDecimal(5385))))
                .quantity(2)
                .price(Money(BigDecimal(5385)))
                .subTotal(null)
                .build()
                .isPriceValid()
        )
    }
}
