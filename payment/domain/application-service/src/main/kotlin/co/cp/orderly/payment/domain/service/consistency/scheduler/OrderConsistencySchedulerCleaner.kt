package co.cp.orderly.payment.domain.service.consistency.scheduler

import ConsistencyState
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Component
open class OrderConsistencySchedulerCleaner(
    private val orderConsistencyHelper: OrderConsistencyUtil? = null
) {
    companion object { private val logger = Logger.getLogger(OrderConsistencySchedulerCleaner::class.java.name) }

    @Transactional
    @Scheduled(cron = "@midnight")
    open fun processConsistencyMessage() {
        val consistencyMessagesResponse =
            orderConsistencyHelper?.getOrderConsistencyMessageByConsistencyStatus(ConsistencyState.COMPLETED)!!
        if (consistencyMessagesResponse.isNotEmpty()) {
            logger.info("Received #${consistencyMessagesResponse.size} OrderConsistencyMessage for clean-up")
            orderConsistencyHelper.deleteOrderConsistencyMessageByConsistencyStatus(ConsistencyState.COMPLETED)
            logger.info("Deleted #${consistencyMessagesResponse.size} OrderConsistencyMessage's")
        }
    }
}
