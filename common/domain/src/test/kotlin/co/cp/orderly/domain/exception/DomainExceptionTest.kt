package co.cp.orderly.domain.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DomainExceptionTest {

    @Test
    fun `should construct DomainException using both constructors implementations`() {
        var domainException = assertThrows<DomainException> {
            throw DomainException("A domain exception just occurred")
        }
        assertEquals(domainException.message, "A domain exception just occurred")

        domainException = assertThrows {
            throw DomainException(
                "A domain exception just occurred calling secondary constructor",
                DomainException("domainException", RuntimeException())
            )
        }

        assertEquals(domainException.message, "A domain exception just occurred calling secondary constructor")
        assertEquals(domainException.cause, domainException.cause)
    }
}
