package co.cp.orderly.order.domain.application.service.ports.output.repository

import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.vos.TrackingId

interface OrderRepository {
    fun saveOrder(order: Order): Order
    fun findByTrackingId(trackingId: TrackingId): Order?
    fun findById(orderId: OrderId?): Order
}
