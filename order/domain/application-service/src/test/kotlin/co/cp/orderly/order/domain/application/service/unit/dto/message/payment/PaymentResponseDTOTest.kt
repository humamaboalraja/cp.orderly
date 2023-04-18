package co.cp.orderly.order.domain.application.service.unit.dto.message.payment

import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.order.domain.application.service.dto.message.payment.PaymentResponseDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant

class PaymentResponseDTOTest {
    private val paymentResponseDTO = PaymentResponseDTO(
        "36418fe4-efc1-45c1-a013-51be6af1bb0e",
        "2d495872-bcbc-4b62-91c5-1c546397d2a1",
        "441c16fd-91de-4089-85c3-2ea388d319db",
        "9ff9538f-26b5-4602-b847-1e06e970c6a9",
        "0ff9538f-26b5-4602-b847-1e06e970c6a9",
        BigDecimal(3142354),
        Instant.parse("2023-03-13T13:58:46.085852Z"),
        PaymentStatus.COMPLETED,
        mutableListOf("Succeeded", "failed", "corrupted")
    )

    @Test
    fun `should construct PaymentResponseDTO and check its data properties`() {
        assertNotNull(paymentResponseDTO.id)
        assertNotNull(paymentResponseDTO.lltId)
        assertNotNull(paymentResponseDTO.orderId)
        assertNotNull(paymentResponseDTO.paymentId)
        assertNotNull(paymentResponseDTO.customerId)
        assertNotNull(paymentResponseDTO.price)
        assertNotNull(paymentResponseDTO.createdAt)
        assertNotNull(paymentResponseDTO.paymentStatus)
        assertNotNull(paymentResponseDTO.errorMessages)
    }

    @Test
    fun `should check the validity of PaymentResponseDTO toString() method`() {
        Assertions.assertEquals(
            paymentResponseDTO.toString(),
            "PaymentResponseDTO(id=36418fe4-efc1-45c1-a013-51be6af1bb0e, " +
                "lltId=2d495872-bcbc-4b62-91c5-1c546397d2a1, " +
                "orderId=441c16fd-91de-4089-85c3-2ea388d319db, " +
                "paymentId=9ff9538f-26b5-4602-b847-1e06e970c6a9, " +
                "customerId=0ff9538f-26b5-4602-b847-1e06e970c6a9, " +
                "price=3142354, " +
                "createdAt=2023-03-13T13:58:46.085852Z, " +
                "paymentStatus=COMPLETED, " +
                "errorMessages=[Succeeded, failed, corrupted])"
        )
    }
}
