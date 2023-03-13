package co.cp.orderly.order.domain.application.service.ports.input.message.listener.shop

import co.cp.orderly.order.domain.application.service.dto.message.shop.ShopApprovalResponseDTO

interface ShopApprovalResponseMessageListener {
    fun orderApproved(shopApprovalResponseDTO: ShopApprovalResponseDTO)
    fun orderRejected(shopApprovalResponseDTO: ShopApprovalResponseDTO)
}
