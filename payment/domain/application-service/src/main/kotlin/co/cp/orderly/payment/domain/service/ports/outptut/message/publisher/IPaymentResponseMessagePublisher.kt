package co.cp.orderly.payment.domain.service.ports.outptut.message.publisher

import ConsistencyState
import co.cp.orderly.payment.domain.service.consistency.model.OrderConsistencyMessage
import java.util.function.BiConsumer

interface IPaymentResponseMessagePublisher {
    fun publish(
        orderConsistencyMessage: OrderConsistencyMessage,
        consistencyCallback: BiConsumer<OrderConsistencyMessage, ConsistencyState>
    )
}
