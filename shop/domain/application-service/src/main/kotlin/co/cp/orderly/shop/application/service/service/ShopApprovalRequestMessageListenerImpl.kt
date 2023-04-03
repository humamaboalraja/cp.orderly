package co.cp.orderly.shop.application.service.service

import co.cp.orderly.shop.application.service.dto.ShopApprovalRequest
import co.cp.orderly.shop.application.service.ports.input.message.listener.ShopApprovalRequestMessageListener
import org.springframework.stereotype.Service

@Service
class ShopApprovalRequestMessageListenerImpl(
    private val shopApprovalRequestHelperService: ShopApprovalRequestHelperService,

) : ShopApprovalRequestMessageListener {

    override fun approveOrder(shopApprovalRequest: ShopApprovalRequest) {
        val orderApprovalEvent = shopApprovalRequestHelperService.saveOrderApproval(shopApprovalRequest)
        orderApprovalEvent.fire()
    }
}
