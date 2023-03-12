package co.cp.orderly.order.domain.core.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OrderDomainExceptionTest {

    @Test
    fun `should construct DomainException using both constructors implementations`() {
        var domainException = assertThrows<OrderDomainException> {
            throw OrderDomainException("An order domain exception has been thrown")
        }
        assertEquals(domainException.message, "An order domain exception has been thrown")

        domainException = assertThrows {
            throw OrderDomainException(
                "An order domain exception has been thrown occurred calling secondary constructor",
                OrderDomainException("orderDomainException", RuntimeException())
            )
        }

        assertEquals(domainException.message, "An order domain exception has been thrown occurred calling secondary constructor")
        assertEquals(domainException.cause, domainException.cause)
    }
}
