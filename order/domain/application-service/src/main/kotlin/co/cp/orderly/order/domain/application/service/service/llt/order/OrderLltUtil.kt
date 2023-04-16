package co.cp.orderly.order.domain.application.service.service.llt.order

import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.exception.OrderNotFoundException
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.logging.Logger

@Component
class OrderLltUtil(private val orderRepository: OrderRepository) {

    companion object { private val logger = Logger.getLogger(OrderLltUtil::class.java.name) }

    fun findOrder(orderId: String): Order {
        val orderResponse = orderRepository.findById(OrderId(UUID.fromString(orderId)))
        if (orderResponse == null) {
            logger.info("Order id #$orderId couldn't be found")
            throw OrderNotFoundException("Order with id $orderId could not be found!")
        }
        return orderResponse
    }

    fun saveOrder(order: Order) = orderRepository.saveOrder(order)

    fun orderStatusToLltStatus(orderStatus: OrderStatus?) =
        when (orderStatus) {
            OrderStatus.PAID -> LongRunningTransactionState.PROCESSING
            OrderStatus.APPROVED -> LongRunningTransactionState.SUCCEEDED
            OrderStatus.CANCELLING -> LongRunningTransactionState.COMPENSATING
            OrderStatus.CANCELED -> LongRunningTransactionState.COMPENSATED
            else -> LongRunningTransactionState.STARTED
        }
}
