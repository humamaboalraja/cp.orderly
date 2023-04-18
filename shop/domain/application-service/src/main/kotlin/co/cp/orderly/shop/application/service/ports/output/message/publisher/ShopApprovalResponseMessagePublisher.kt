package co.cp.orderly.shop.application.service.ports.output.message.publisher

import ConsistencyState
import co.cp.orderly.shop.application.service.consistency.model.OrderConsistencyMessage
import java.util.function.BiConsumer

interface ShopApprovalResponseMessagePublisher {
    fun publish(
        orderConsistencyMessage: OrderConsistencyMessage,
        consistencyCallback: BiConsumer<OrderConsistencyMessage, ConsistencyState>?
    )
}
