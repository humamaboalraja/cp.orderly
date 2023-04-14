package co.cp.orderly.order.domain.application.service.service

import co.cp.orderly.order.domain.application.service.dto.message.shop.ShopApprovalResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.shop.ShopApprovalResponseMessageListener
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
open class ShopApprovalResponseMessageListenerImpl(
) : ShopApprovalResponseMessageListener {


    override fun orderApproved(shopApprovalResponseDTO: ShopApprovalResponseDTO) =
        TODO("Not yet implemented")

    override fun orderRejected(shopApprovalResponseDTO: ShopApprovalResponseDTO) =
        TODO("Not yet implemented")
}
