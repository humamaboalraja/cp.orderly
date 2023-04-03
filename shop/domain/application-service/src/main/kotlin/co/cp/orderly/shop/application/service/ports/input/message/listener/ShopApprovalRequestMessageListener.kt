package co.cp.orderly.shop.application.service.ports.input.message.listener

import co.cp.orderly.shop.application.service.dto.ShopApprovalRequest

interface ShopApprovalRequestMessageListener {
    fun approveOrder(shopApprovalRequest: ShopApprovalRequest)
}
