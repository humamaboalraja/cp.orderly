package co.cp.orderly.order.domain.application.service.ports.output.message.publisher.shop

import ConsistencyState
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import java.util.function.BiConsumer

interface ShopApprovalRequestMessagePublisher {
    fun publish(
        orderPaymentConsistencyMessage: OrderApprovalConsistencyMessage,
        consistencyCallback: BiConsumer<OrderApprovalConsistencyMessage, ConsistencyState>
    )
}
