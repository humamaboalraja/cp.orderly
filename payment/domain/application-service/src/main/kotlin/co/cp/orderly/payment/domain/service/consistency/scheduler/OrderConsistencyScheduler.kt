package co.cp.orderly.payment.domain.service.consistency.scheduler

import ConsistencyState
import co.cp.orderly.payment.domain.service.consistency.model.OrderConsistencyMessage
import co.cp.orderly.payment.domain.service.ports.outptut.message.publisher.IPaymentResponseMessagePublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer
import java.util.logging.Logger

@Component
open class OrderConsistencyScheduler(
    private val orderConsistencyUtil: OrderConsistencyUtil,
    private val paymentResponseMessagePublisher: IPaymentResponseMessagePublisher,

) {

    companion object { private val logger = Logger.getLogger(OrderConsistencyScheduler::class.java.name) }

    @Transactional
    @Scheduled(
        fixedRateString = "\${payment-service.consistency-scheduler-fixed-rate}",
        initialDelayString = "\${payment-service.consistency-scheduler-initial-delay}"
    )
    open fun processConsistencyMessage() {
        val consistencyMessagesResponse: List<OrderConsistencyMessage> =
            orderConsistencyUtil.getOrderConsistencyMessageByConsistencyStatus(ConsistencyState.STARTED)!!
        if (consistencyMessagesResponse.isNotEmpty()) {
            logger.info(
                "Received #${consistencyMessagesResponse.size} OrderConsistencyMessage " +
                    "id's #${consistencyMessagesResponse.map { it.id.toString() }.joinToString { "," }}"
            )
            consistencyMessagesResponse.forEach(
                Consumer { orderConsistencyMessage: OrderConsistencyMessage? ->
                    paymentResponseMessagePublisher.publish(
                        orderConsistencyMessage!!, orderConsistencyUtil::updateConsistencyMessage
                    )
                }
            )
            logger.info("${consistencyMessagesResponse.size} OrderConsistencyMessage's were sent to the queue",)
        }
    }
}
