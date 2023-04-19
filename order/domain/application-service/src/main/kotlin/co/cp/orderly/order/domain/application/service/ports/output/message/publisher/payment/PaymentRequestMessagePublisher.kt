package co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment

import ConsistencyState
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import java.util.function.BiConsumer

interface PaymentRequestMessagePublisher {
    fun publish(
        orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage,
        consistencyCallback: BiConsumer<OrderPaymentConsistencyMessage, ConsistencyState>
    )
}
