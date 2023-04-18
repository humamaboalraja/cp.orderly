package co.cp.orderly.shop.application.service.consistency.scheduler

import ConsistencyScheduler
import ConsistencyState
import co.cp.orderly.shop.application.service.ports.output.message.publisher.ShopApprovalResponseMessagePublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer
import java.util.logging.Logger

@Component
open class OrderConsistencyScheduler(
    private val orderConsistencyUtil: OrderConsistencyUtil?,
    private val responseMessagePublisher: ShopApprovalResponseMessagePublisher

) : ConsistencyScheduler {

    companion object { private val logger = Logger.getLogger(OrderConsistencyScheduler::class.java.name) }

    @Transactional
    @Scheduled(
        fixedRateString = "\${shop-service.consistency-scheduler-fixed-rate}",
        initialDelayString = "\${shop-service.consistency-scheduler-initial-delay}"
    )
    override fun processConsistencyMessage() {
        val consistencyMessagesResponse =
            orderConsistencyUtil?.getOrderConsistencyMessageByConsistencyState(ConsistencyState.STARTED)
        if (consistencyMessagesResponse!!.isNotEmpty()) {
            logger.info(
                "Received #${consistencyMessagesResponse.size} OrderConsistencyMessage's " +
                    "ids #${consistencyMessagesResponse.map { it.id.toString() }.joinToString { "," }}"
            )
            consistencyMessagesResponse.forEach(
                Consumer {
                    responseMessagePublisher.publish(it, orderConsistencyUtil!!::updateConsistencyState)
                }
            )

            logger.info("{consistencyMessagesResponse?.size} OrderConsistencyMessage's have been sent to the message queue")
        }
    }
}
