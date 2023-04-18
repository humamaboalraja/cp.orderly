package co.cp.orderly.shop.application.service.ports.output.repository

import ConsistencyState
import co.cp.orderly.shop.application.service.consistency.model.OrderConsistencyMessage
import java.util.UUID

interface IOrderConsistencyRepository {

    fun save(orderConsistencyMessage: OrderConsistencyMessage?): OrderConsistencyMessage?

    fun findByTypeAndLltIdAndConsistencyState(
        type: String?,
        lltId: UUID?,
        consistencyState: ConsistencyState?
    ): OrderConsistencyMessage?

    fun deleteByTypeAndConsistencyState(type: String?, consistencyStatus: ConsistencyState?)

    fun findByTypeAndConsistencyState(type: String?, consistencyStatus: ConsistencyState?): List<OrderConsistencyMessage>?
}
