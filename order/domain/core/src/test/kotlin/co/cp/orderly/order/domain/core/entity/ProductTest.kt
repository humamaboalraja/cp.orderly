package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.ProductId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class ProductTest {
    @Test
    fun `should call and construct Product and its parent`() {
        val product = Product(
            ProductId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")),
            "Fresh Bananas",
            Money(BigDecimal(23454))
        )
        assertEquals(product.getId(), ProductId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")))
        assertEquals(product.name, "Fresh Bananas")
        assertEquals(product.price, Money(BigDecimal(23454)))
        assertEquals(product.toString(), "BaseEntity(id=BaseId(value=441c16fd-91de-4089-85c3-2ea388d319db))")
    }
}
