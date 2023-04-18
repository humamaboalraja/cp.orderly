package co.cp.orderly.order.domain.application.service.consistency.scheduler.approval

import ConsistencyScheduler
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class ShopApprovalConsistencyCleanerScheduler(
    private val approvalConsistencyUtil: ApprovalConsistencyUtil
) : ConsistencyScheduler {

    companion object { private val logger = Logger.getLogger(ShopApprovalConsistencyCleanerScheduler::class.java.name) }

    @Scheduled(cron = "@midnight")
    override fun processConsistencyMessage() {
        val consistencyMessagesResponse =
            approvalConsistencyUtil.getApprovalConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.COMPLETED,
                LongRunningTransactionState.SUCCEEDED,
                LongRunningTransactionState.FAILED,
                LongRunningTransactionState.COMPENSATED,
            )
        if (consistencyMessagesResponse.isNotEmpty()) {
            logger.info(
                "Received ${consistencyMessagesResponse.size} OrderApprovalConsistencyMessage for clean-up. " +
                    "Payloads: ${
                    consistencyMessagesResponse.map(OrderApprovalConsistencyMessage::payload).joinToString { "\n" }}"

            )
            approvalConsistencyUtil.deleteApprovalConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.COMPLETED,
                LongRunningTransactionState.SUCCEEDED,
                LongRunningTransactionState.FAILED,
                LongRunningTransactionState.COMPENSATED,
            )
            logger.info("${consistencyMessagesResponse.size} OrderApprovalConsistencyMessage's have been deleted")
        }
    }
}
