package co.cp.orderly.order.domain.application.service.service.commands

import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderQueryDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderResponseDTO
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.application.service.utils.dataMapper.OrderApplicationServiceDataMapper
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.exception.OrderNotFoundException
import co.cp.orderly.order.domain.core.vos.TrackingId
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Component
open class OrderTrackCommand(
    private val orderApplicationServiceDataMapper: OrderApplicationServiceDataMapper,
    private val orderRepository: OrderRepository

) {

    private val logger = Logger.getLogger(OrderTrackCommand::class.java.name)

    @Transactional(readOnly = true)
    open fun trackOrder(trackOrderQueryDTO: TrackOrderQueryDTO): TrackOrderResponseDTO? {
        val order: Order? = orderRepository.findByTrackingId(TrackingId(trackOrderQueryDTO.orderTrackingId))
        when (order) {
            null -> {
                logger.warning("Couldn't find Order #${trackOrderQueryDTO.orderTrackingId}")
                throw OrderNotFoundException("Couldn't find Order #${trackOrderQueryDTO.orderTrackingId}")
            }
        }
        return order?.let { orderResult -> orderApplicationServiceDataMapper.orderToTrackOrderResponse(orderResult) }
    }
}
