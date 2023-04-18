package co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment

import ConsistencyState
import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import co.cp.orderly.order.domain.core.event.OrderCancelledEvent
import java.util.function.BiConsumer

interface PaymentRequestMessagePublisher : DomainEventPublisher<OrderCancelledEvent> {
    fun publish(
        orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage,
        consistencyCallback: BiConsumer<OrderPaymentConsistencyMessage, ConsistencyState>
    )
}
