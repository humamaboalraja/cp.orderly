package co.cp.orderly.shop.application.service.consistency.scheduler

import ConsistencyScheduler
import ConsistencyState
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Component
open class OrderConsistencySchedulerCleaner(
    private val orderConsistencyUtil: OrderConsistencyUtil
) : ConsistencyScheduler {

    companion object { private val logger = Logger.getLogger(OrderConsistencySchedulerCleaner::class.java.name) }

    @Transactional
    @Scheduled(cron = "@midnight")
    override fun processConsistencyMessage() {
        val consistencyMessagesResponse = orderConsistencyUtil.getOrderConsistencyMessageByConsistencyState(
            ConsistencyState.COMPLETED
        )
        if (consistencyMessagesResponse!!.isNotEmpty()) {
            logger.info("Received #${consistencyMessagesResponse.size} OrderConsistencyMessage's to clean",)
            orderConsistencyUtil.deleteOrderConsistencyMessageByConsistencyState(ConsistencyState.COMPLETED)
            logger.info("#${consistencyMessagesResponse.size} OrderConsistencyMessage's have been deleted")
        }
    }
}
