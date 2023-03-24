package co.cp.orderly.order.data.order.adapter

import co.cp.orderly.order.data.order.mapper.OrderDataMapper
import co.cp.orderly.order.data.order.repository.OrderPersistenceRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.vos.TrackingId
import org.springframework.stereotype.Component

@Component
class OrderRepositoryImpl(
    private val orderPersistenceRepository: OrderPersistenceRepository,
    private val orderDataMapper: OrderDataMapper
) : OrderRepository {
    override fun saveOrder(order: Order): Order =
        orderDataMapper.orderEntityToOrder(
            orderPersistenceRepository.save(
                orderDataMapper.orderToOrderEntity(order)
            )
        )

    override fun findByTrackingId(trackingId: TrackingId): Order? =
        orderDataMapper.orderEntityToOrder(
            orderPersistenceRepository.findByTrackingId(trackingId.getValue())!!
        )
}
