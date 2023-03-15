package co.cp.orderly.order.domain.application.service.service

import co.cp.orderly.order.domain.application.service.dto.message.shop.ShopApprovalResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.shop.ShopApprovalResponseMessageListener
import co.cp.orderly.order.domain.application.service.service.commands.OrderCreateCommand
import org.jboss.logging.Logger
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class ShopApprovalResponseMessageListenerImpl : ShopApprovalResponseMessageListener {

    private val logger = Logger.getLogger(OrderCreateCommand::class.java)

    override fun orderApproved(shopApprovalResponseDTO: ShopApprovalResponseDTO) {
    }

    override fun orderRejected(shopApprovalResponseDTO: ShopApprovalResponseDTO) {
        TODO("Not yet implemented")
    }
}
