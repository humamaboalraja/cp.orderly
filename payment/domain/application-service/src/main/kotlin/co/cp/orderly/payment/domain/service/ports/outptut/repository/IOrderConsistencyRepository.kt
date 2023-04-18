package co.cp.orderly.payment.domain.service.ports.outptut.repository

import ConsistencyState
import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.payment.domain.service.consistency.model.OrderConsistencyMessage
import java.util.UUID

interface IOrderConsistencyRepository {
    fun save(orderConsistencyMessage: OrderConsistencyMessage): OrderConsistencyMessage?

    fun findByTypeAndConsistencyState(type: String, status: ConsistencyState): List<OrderConsistencyMessage>?

    fun findByTypeAndLltIdAndPaymentStatusAndConsistencyState(
        type: String?,
        lltId: UUID?,
        paymentStatus: PaymentStatus?,
        consistencyStatus: ConsistencyState?
    ): OrderConsistencyMessage?

    fun deleteByTypeAndConsistencyState(type: String, status: ConsistencyState)
}
