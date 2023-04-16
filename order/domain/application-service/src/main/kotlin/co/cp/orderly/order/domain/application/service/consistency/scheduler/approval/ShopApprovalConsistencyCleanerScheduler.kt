package co.cp.orderly.order.domain.application.service.consistency.scheduler.approval

import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class ShopApprovalConsistencyCleanerScheduler(
    private val approvalConsistencyUtil: ApprovalConsistencyUtil
) {

    companion object { private val logger = Logger.getLogger(ShopApprovalConsistencyCleanerScheduler::class.java.name) }

    @Scheduled(cron = "@midnight")
    fun processOutboxMessage() {
        val outboxMessagesResponse: List<OrderApprovalConsistencyMessage> =
            approvalConsistencyUtil.getApprovalConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.COMPLETED,
                LongRunningTransactionState.SUCCEEDED,
                LongRunningTransactionState.FAILED,
                LongRunningTransactionState.COMPENSATED,
            )
        if (outboxMessagesResponse.isNotEmpty()) {
            logger.info(
                "Received ${outboxMessagesResponse.size} OrderApprovalOutboxMessage for clean-up. " +
                    "Payloads: ${
                    outboxMessagesResponse.map(OrderApprovalConsistencyMessage::payload).joinToString { "\n" }}"

            )
            approvalConsistencyUtil.deleteApprovalConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.COMPLETED,
                LongRunningTransactionState.SUCCEEDED,
                LongRunningTransactionState.FAILED,
                LongRunningTransactionState.COMPENSATED,
            )
            logger.info("${outboxMessagesResponse.size} OrderApprovalOutboxMessages have been deleted!")
        }
    }
}
