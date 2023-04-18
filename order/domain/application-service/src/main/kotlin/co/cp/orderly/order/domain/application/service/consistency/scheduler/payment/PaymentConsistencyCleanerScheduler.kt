package co.cp.orderly.order.domain.application.service.consistency.scheduler.payment

import ConsistencyScheduler
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class PaymentConsistencyCleanerScheduler(
    private val paymentConsistencyUtil: PaymentConsistencyUtil
) : ConsistencyScheduler {

    companion object { private val logger = Logger.getLogger(PaymentConsistencyCleanerScheduler::class.java.name) }

    @Scheduled(cron = "@midnight")
    override fun processConsistencyMessage() {
        val consistencyMessagesResponse: List<OrderPaymentConsistencyMessage> =
            paymentConsistencyUtil.getPaymentConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.COMPLETED,
                LongRunningTransactionState.SUCCEEDED,
                LongRunningTransactionState.FAILED,
                LongRunningTransactionState.COMPENSATED
            )
        if (consistencyMessagesResponse.isNotEmpty()) {
            logger.info(
                "Received ${consistencyMessagesResponse.size} OrderPaymentConsistencyMessage's to clean. " +
                    "Payloads #${
                    consistencyMessagesResponse.map(OrderPaymentConsistencyMessage::payload).joinToString { "\n" }
                    }"
            )

            paymentConsistencyUtil.deletePaymentConsistencyMessageByConsistencyStateAndLltState(
                ConsistencyState.COMPLETED,
                LongRunningTransactionState.SUCCEEDED,
                LongRunningTransactionState.FAILED,
                LongRunningTransactionState.COMPENSATED
            )
            logger.info("${consistencyMessagesResponse.size} OrderPaymentConsistencyMessage been deleted")
        }
    }
}
