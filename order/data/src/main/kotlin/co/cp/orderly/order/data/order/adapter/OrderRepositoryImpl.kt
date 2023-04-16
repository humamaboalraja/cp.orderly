package co.cp.orderly.order.data.order.adapter

import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.order.data.order.mapper.OrderDataMapperUtil
import co.cp.orderly.order.data.order.repository.OrderPersistenceRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.vos.TrackingId
import org.springframework.stereotype.Component

@Component
class OrderRepositoryImpl(
    private val orderPersistenceRepository: OrderPersistenceRepository,
    private val orderDataMapperUtil: OrderDataMapperUtil
) : OrderRepository {
    override fun saveOrder(order: Order): Order =
        orderDataMapperUtil.orderEntityToOrder(
            orderPersistenceRepository.save(
                orderDataMapperUtil.orderToOrderEntity(order)
            )
        )

    override fun findById(orderId: OrderId?): Order =
        orderPersistenceRepository.findById(orderId!!.getValue()).map(orderDataMapperUtil::orderEntityToOrder).get()

    override fun findByTrackingId(trackingId: TrackingId): Order =
        orderDataMapperUtil.orderEntityToOrder(
            orderPersistenceRepository.findByTrackingId(trackingId.getValue())!!
        )
}
