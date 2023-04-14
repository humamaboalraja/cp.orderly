package co.cp.orderly.order.domain.application.service.service.message.shop

import co.cp.orderly.order.domain.application.service.dto.message.shop.ShopApprovalResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.shop.ShopApprovalResponseMessageListener
import co.cp.orderly.order.domain.application.service.service.llt.order.OrderApprovalLlt
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.logging.Logger

@Validated
@Service
open class ShopApprovalResponseMessageListenerImpl(
    private val orderApprovalLlt: OrderApprovalLlt
) : ShopApprovalResponseMessageListener {

    companion object { private val logger = Logger.getLogger(ShopApprovalResponseMessageListenerImpl::class.java.name) }

    override fun orderApproved(shopApprovalResponseDTO: ShopApprovalResponseDTO) {
        orderApprovalLlt.process(shopApprovalResponseDTO)
        logger.info("Order #${shopApprovalResponseDTO.orderId} has been approved")
    }

    override fun orderRejected(shopApprovalResponseDTO: ShopApprovalResponseDTO) {
        orderApprovalLlt.rollback(shopApprovalResponseDTO)
        logger.info(
            "Order #${shopApprovalResponseDTO.orderId}  Approval's Llt rollback operation is completed " +
                "with the following messages messages: #${shopApprovalResponseDTO.errorMessages.joinToString { "," }}"
        )
    }
}
