package co.cp.orderly.order.domain.application.service.unit.dto.message.shop

import co.cp.orderly.domain.vos.OrderApprovalStatus
import co.cp.orderly.order.domain.application.service.dto.message.shop.ShopApprovalResponseDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.Instant

class ShopApprovalResponseDTOTest {
    private val shopApprovalResponseDTO = ShopApprovalResponseDTO(
        "36418fe4-efc1-45c1-a013-51be6af1bb0e",
        "2d495872-bcbc-4b62-91c5-1c546397d2a1",
        "441c16fd-91de-4089-85c3-2ea388d319db",
        "9ff9538f-26b5-4602-b847-1e06e970c6a9",
        Instant.parse("2023-03-13T13:58:46.085852Z"),
        OrderApprovalStatus.APPROVED,
        mutableListOf("Succeeded", "failed", "corrupted")
    )

    @Test
    fun `should construct ShopApprovalResponseDTO and check its data properties`() {
        assertNotNull(shopApprovalResponseDTO.id)
        assertNotNull(shopApprovalResponseDTO.lltId)
        assertNotNull(shopApprovalResponseDTO.orderId)
        assertNotNull(shopApprovalResponseDTO.shopId)
        assertNotNull(shopApprovalResponseDTO.createdAt)
        assertNotNull(shopApprovalResponseDTO.orderApprovalStatus)
        assertNotNull(shopApprovalResponseDTO.errorMessages)
    }

    @Test
    fun `should check the validity of ShopApprovalResponseDTO toString() method`() {
        assertEquals(
            shopApprovalResponseDTO.toString(),
            "ShopApprovalResponseDTO(id=36418fe4-efc1-45c1-a013-51be6af1bb0e, " +
                "lltId=2d495872-bcbc-4b62-91c5-1c546397d2a1, " +
                "orderId=441c16fd-91de-4089-85c3-2ea388d319db, " +
                "shopId=9ff9538f-26b5-4602-b847-1e06e970c6a9, " +
                "createdAt=2023-03-13T13:58:46.085852Z, " +
                "orderApprovalStatus=APPROVED, " +
                "errorMessages=[Succeeded, failed, corrupted])"
        )
    }
}
