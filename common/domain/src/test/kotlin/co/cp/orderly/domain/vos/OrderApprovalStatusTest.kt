package co.cp.orderly.domain.vos

import co.cp.orderly.domain.vos.PaymentStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OrderApprovalStatusTest {
    private val orderApprovalStatus = OrderApprovalStatus.values()

    @Test
    fun `should call OrderApprovalStatus and inspect its values`() {
        assertEquals(orderApprovalStatus[0], OrderApprovalStatus.APPROVED)
        assertEquals(orderApprovalStatus[1], OrderApprovalStatus.REJECTED)
    }
}
