package co.cp.orderly.order.domain.core.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OrderNotFoundExceptionTest {

    @Test
    fun `should construct OrderNotFoundException using both constructors implementations`() {
        var orderNotFoundException = assertThrows<OrderNotFoundException> {
            throw OrderNotFoundException("An order not found exception has been thrown")
        }
        assertEquals(orderNotFoundException.message, "An order not found exception has been thrown")

        orderNotFoundException = assertThrows {
            throw OrderNotFoundException(
                "An order not found exception has been thrown occurred calling secondary constructor",
                OrderNotFoundException("orderNotFoundDomainException", RuntimeException())
            )
        }

        assertEquals(orderNotFoundException.message, "An order not found exception has been thrown occurred calling secondary constructor")
        assertEquals(orderNotFoundException.cause, orderNotFoundException.cause)
    }
}
