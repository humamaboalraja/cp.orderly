package co.cp.orderly.order.domain.application.service.ports.output.message.publisher.shop

import ConsistencyState
import co.cp.orderly.domain.event.publisher.DomainEventPublisher
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import co.cp.orderly.order.domain.core.event.OrderPaidEvent
import java.util.function.BiConsumer

interface ShopApprovalRequestMessagePublisher : DomainEventPublisher<OrderPaidEvent> {
    fun publish(
        orderPaymentConsistencyMessage: OrderApprovalConsistencyMessage,
        consistencyCallback: BiConsumer<OrderApprovalConsistencyMessage, ConsistencyState>
    )
}
