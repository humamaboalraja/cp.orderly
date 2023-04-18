package co.cp.orderly.order.domain.application.service.consistency.scheduler.approval

import ConsistencyScheduler
import ConsistencyState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.shop.ShopApprovalRequestMessagePublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer
import java.util.logging.Logger

@Component
open class ShopApprovalConsistencyScheduler(
    private val approvalConsistencyHelper: ApprovalConsistencyUtil,
    private val shopApprovalRequestMessagePublisher: ShopApprovalRequestMessagePublisher

) : ConsistencyScheduler {
    companion object { private val logger = Logger.getLogger(ShopApprovalConsistencyCleanerScheduler::class.java.name) }

    @Transactional
    @Scheduled(
        fixedDelayString = "\${order-service.consistency-scheduler-fixed-rate}",
        initialDelayString = "\${order-service.consistency-scheduler-initial-delay}"
    )
    override fun processConsistencyMessage() {
        val consistencyMessagesResponse: List<OrderApprovalConsistencyMessage> =
            approvalConsistencyHelper.getApprovalConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.STARTED,
                LongRunningTransactionState.PROCESSING
            )
        if (!consistencyMessagesResponse.isNullOrEmpty()) {
            val consistencyMessages = consistencyMessagesResponse.map {
                consistencyMessage ->
                consistencyMessage.id.toString()
            }.joinToString { "," }

            logger.info(
                "Received ${consistencyMessagesResponse.size} OrderApprovalConsistencyMessages " +
                    "ids: $consistencyMessages are being sent to the broker"
            )

            consistencyMessagesResponse.forEach(
                Consumer {
                    shopApprovalRequestMessagePublisher.publish(it) {
                        orderApprovalConsistencyMessage: OrderApprovalConsistencyMessage,
                        consistencyStatus: ConsistencyState ->
                        updateConsistencyStatus(orderApprovalConsistencyMessage, consistencyStatus)
                    }
                }
            )
            logger.info("${consistencyMessagesResponse.size} OrderApprovalConsistencyMessage were sent to the broker")
        }
    }

    private fun updateConsistencyStatus(orderApprovalConsistencyMessage: OrderApprovalConsistencyMessage, consistencyStatus: ConsistencyState) {
        orderApprovalConsistencyMessage.consistencyState = consistencyStatus
        approvalConsistencyHelper.save(orderApprovalConsistencyMessage)
        logger.info("OrderApprovalConsistencyMessage is updated with consistency status: ${consistencyStatus.name}")
    }
}
