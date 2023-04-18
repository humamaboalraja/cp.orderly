package co.cp.orderly.shop.application.service.service

import ConsistencyState
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.shop.application.service.consistency.scheduler.OrderConsistencyUtil
import co.cp.orderly.shop.application.service.dto.ShopApprovalRequest
import co.cp.orderly.shop.application.service.mapper.ShopApplicationServiceDataMapper
import co.cp.orderly.shop.application.service.ports.output.message.publisher.ShopApprovalResponseMessagePublisher
import co.cp.orderly.shop.application.service.ports.output.repository.IOrderApprovalRepository
import co.cp.orderly.shop.application.service.ports.output.repository.IShopRepository
import co.cp.orderly.shop.domain.core.entity.Shop
import co.cp.orderly.shop.domain.core.exception.ShopNotFoundException
import co.cp.orderly.shop.domain.core.service.IShopDomainService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.logging.Logger

@Component
open class ShopApprovalRequestHelperService(
    private val shopDomainService: IShopDomainService,
    private val shopDataMapper: ShopApplicationServiceDataMapper,
    private val shopRepository: IShopRepository,
    private val orderApprovalRepository: IOrderApprovalRepository,
    private val shopApprovalResponseMessagePublisher: ShopApprovalResponseMessagePublisher,
    private val orderConsistencyUtil: OrderConsistencyUtil,

) {
    companion object { private val logger = Logger.getLogger(ShopApprovalRequestHelperService::class.java.name) }

    @Transactional
    open fun saveOrderApproval(shopApprovalRequest: ShopApprovalRequest) {
        if (publishIfConsistencyMessageProcessed(shopApprovalRequest)) {
            logger.info("Consistency message of llt #${shopApprovalRequest.lltId} has already been saved persisted")
            return
        }
        logger.info("Processing shop request for order #${shopApprovalRequest.orderId}")
        val errorMessages = mutableListOf<String>()
        val shop = findShop(shopApprovalRequest)
        val orderApprovedEvent = shopDomainService.validateOrder(shop, errorMessages)
        orderApprovalRepository.save(shop.orderApproval!!)
        orderConsistencyUtil
            .saveOrderConsistencyMessage(
                shopDataMapper.orderApprovalEventToOrderEventPayloadDTO(orderApprovedEvent),
                orderApprovedEvent.orderApproval.orderApprovalStatus,
                ConsistencyState.STARTED,
                UUID.fromString(shopApprovalRequest.lltId)
            )
    }

    private fun findShop(shopApprovalRequest: ShopApprovalRequest): Shop {
        val shop = shopDataMapper.shopApprovalRequestToShop(shopApprovalRequest)
        val shopResult = shopRepository.findShopDetails(shop)
        if (shopResult == null) {
            logger.info("Shop #${shop.getId()?.getValue()} not found")
            throw ShopNotFoundException("Shop #${shop.getId()?.getValue()} not found")
        }

        shop.changeShopStatusTo(shopResult.isActive!!)
        shop.orderData?.products?.forEach {
            shopResult.orderData?.products?.forEach { product ->
                if (product.getId() == it.getId()) product.updateProductStateToConfirmed(
                    product.name, product.price, product.isAvailable
                )
            }
        }

        shop.orderData?.setId(OrderId(UUID.fromString(shopApprovalRequest.orderId)))
        return shop
    }

    private fun publishIfConsistencyMessageProcessed(shopApprovalRequest: ShopApprovalRequest): Boolean {
        val orderConsistencyMessage =
            orderConsistencyUtil.getCompletedOrderConsistencyMessageByLltIdAndConsistencyState(
                UUID.fromString(shopApprovalRequest.lltId), ConsistencyState.COMPLETED
            )
        if (orderConsistencyMessage != null) {
            shopApprovalResponseMessagePublisher.publish(
                orderConsistencyMessage, orderConsistencyUtil::updateConsistencyState
            )
            return true
        }
        return false
    }
}
