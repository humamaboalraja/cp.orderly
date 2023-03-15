package co.cp.orderly.domain.vos

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PaymentStatusTest {
    private val paymentStatus = PaymentStatus.values()

    @Test
    fun `should call PaymentStatus and inspect its values`() {
        assertEquals(paymentStatus[0], PaymentStatus.COMPLETED)
        assertEquals(paymentStatus[1], PaymentStatus.CANCELLED)
        assertEquals(paymentStatus[2], PaymentStatus.FAILED)
    }
}
