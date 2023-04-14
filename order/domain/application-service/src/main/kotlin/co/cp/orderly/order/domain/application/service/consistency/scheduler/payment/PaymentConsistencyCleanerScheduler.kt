package co.cp.orderly.order.domain.application.service.consistency.scheduler.payment

import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class PaymentConsistencyCleanerScheduler(
    private val paymentConsistencyUtil: PaymentConsistencyUtil
) {

    companion object { private val logger = Logger.getLogger(PaymentConsistencyCleanerScheduler::class.java.name) }

    @Scheduled(cron = "@midnight")
    fun processConsistencyMessage() {
        val outboxMessagesResponse: List<OrderPaymentConsistencyMessage> =
            paymentConsistencyUtil.getPaymentConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.COMPLETED,
                LongRunningTransactionState.SUCCEEDED,
                LongRunningTransactionState.FAILED,
                LongRunningTransactionState.COMPENSATED
            )
        if (outboxMessagesResponse.isNotEmpty()) {
            logger.info(
                "Received ${outboxMessagesResponse.size} OrderPaymentConsistencyMessage's to clean. " +
                    "Payloads #${
                    outboxMessagesResponse.map(OrderPaymentConsistencyMessage::payload).joinToString { "\n" }
                    }"
            )

            paymentConsistencyUtil.deletePaymentConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.COMPLETED,
                LongRunningTransactionState.SUCCEEDED,
                LongRunningTransactionState.FAILED,
                LongRunningTransactionState.COMPENSATED
            )
            logger.info("${outboxMessagesResponse.size} OrderPaymentConsistencyMessage been deleted")
        }
    }
}
