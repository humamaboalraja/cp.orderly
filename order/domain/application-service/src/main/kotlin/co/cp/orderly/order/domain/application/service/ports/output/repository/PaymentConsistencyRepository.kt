package co.cp.orderly.order.domain.application.service.ports.output.repository

import ConsistencyState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import java.util.UUID

interface PaymentConsistencyRepository {
    fun save(orderApprovalConsistencyMessage: OrderPaymentConsistencyMessage?): OrderPaymentConsistencyMessage?

    fun findByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState
    ): List<OrderPaymentConsistencyMessage>

    fun findByTypeAndLltIdAndLltState(
        type: String?,
        lltId: UUID?,
        vararg lltState: LongRunningTransactionState
    ): OrderPaymentConsistencyMessage

    fun deleteByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState
    )
}
