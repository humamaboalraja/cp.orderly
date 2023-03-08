package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.ProductId
import co.cp.orderly.domain.vos.ShopId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class ShopTest {
    @Test
    fun `should call and construct Shop and its parent using Shop's builder instance`() {
        val shop = Shop.Builder(
            ShopId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")),
            listOf(
                Product(
                    ProductId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")),
                    "Fresh Apples",
                    Money(BigDecimal(6453))
                )
            ),
            true
        )
        assertEquals(shop.build().getId(), ShopId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")))
        assertEquals(
            shop.build().products,
            listOf(
                Product(
                    ProductId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")),
                    "Fresh Apples",
                    Money(BigDecimal(6453))
                )
            )
        )
        assertEquals(shop.build().active, true)
        assertEquals(shop.build().toString(), "BaseEntity(id=BaseId(value=441c16fd-91de-4089-85c3-2ea388d319db))")

        assertEquals(
            Shop.builder()
                .shopId(ShopId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")))
                .products(
                    listOf(
                        Product(
                            ProductId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")),
                            "Fresh Apples",
                            Money(BigDecimal(6453))
                        )
                    )
                )
                .active(null).shopId(null).build().getId(),
            null
        )
    }
}
