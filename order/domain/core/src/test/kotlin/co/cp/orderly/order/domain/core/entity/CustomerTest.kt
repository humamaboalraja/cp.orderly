package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.vos.CustomerId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class CustomerTest {
    @Test
    fun `should call and construct Customer and its parent`() {
        val customer = Customer()
        customer.setId(CustomerId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")))
        assertEquals(customer.getId(), CustomerId(UUID.fromString("441c16fd-91de-4089-85c3-2ea388d319db")))
        assertEquals(customer.toString(), "BaseEntity(id=BaseId(value=441c16fd-91de-4089-85c3-2ea388d319db))")
    }
}
