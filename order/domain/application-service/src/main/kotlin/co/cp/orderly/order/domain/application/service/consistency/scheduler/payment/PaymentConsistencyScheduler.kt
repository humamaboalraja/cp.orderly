package co.cp.orderly.order.domain.application.service.consistency.scheduler.payment

import ConsistencyScheduler
import ConsistencyState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment.PaymentRequestMessagePublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer
import java.util.logging.Logger

@Component
open class PaymentConsistencyScheduler(
    private val paymentConsistencyHelper: PaymentConsistencyUtil,
    val paymentRequestMessagePublisher: PaymentRequestMessagePublisher
) : ConsistencyScheduler {

    companion object { private val logger = Logger.getLogger(PaymentConsistencyScheduler::class.java.name) }

    @Transactional
    @Scheduled(
        fixedDelayString = "\${order-service.consistency-scheduler-fixed-rate}",
        initialDelayString = "\${order-service.consistency-scheduler-initial-delay}"
    )
    override fun processConsistencyMessage() {
        val consistencyMessagesResponse =
            paymentConsistencyHelper.getPaymentConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.STARTED,
                LongRunningTransactionState.STARTED,
                LongRunningTransactionState.COMPENSATING
            )
        if (!consistencyMessagesResponse.isNullOrEmpty()) {
            logger.info(
                "Received ${consistencyMessagesResponse.size} OrderPaymentConsistencyMessage's - " +
                    "ids: ${consistencyMessagesResponse.map { it.id.toString() }.joinToString { "," }}, " +
                    "are being sent to the broker"
            )
            consistencyMessagesResponse.forEach(
                Consumer { consistencyMessage: OrderPaymentConsistencyMessage ->
                    paymentRequestMessagePublisher.publish(consistencyMessage) {
                        orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage,
                        consistencyState: ConsistencyState ->
                        updateConsistencyState(orderPaymentConsistencyMessage, consistencyState)
                    }
                }
            )
            logger.info("${consistencyMessagesResponse.size} OrderPaymentConsistencyMessages were sent to the broker")
        }
    }

    private fun updateConsistencyState(
        orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage,
        consistencyState: ConsistencyState
    ) {
        orderPaymentConsistencyMessage.consistencyState = consistencyState
        paymentConsistencyHelper.save(orderPaymentConsistencyMessage)
        logger.info("OrderPaymentConsistencyMessage is updated with consistency status: ${consistencyState.name}")
    }
}
