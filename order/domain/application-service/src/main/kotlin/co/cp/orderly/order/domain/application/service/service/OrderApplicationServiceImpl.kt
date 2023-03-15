package co.cp.orderly.order.domain.application.service.service

import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderQueryDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.service.OrderApplicationService
import co.cp.orderly.order.domain.application.service.service.commands.OrderCreateCommand
import co.cp.orderly.order.domain.application.service.service.commands.OrderTrackCommand
import co.cp.orderly.order.domain.core.service.OrderDomainServiceImpl
import org.jboss.logging.Logger
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class OrderApplicationServiceImpl(
    private val orderCreateCommand: OrderCreateCommand,
    private val orderTrackCommand: OrderTrackCommand,
) : OrderApplicationService {

    companion object { private val logger = Logger.getLogger(OrderDomainServiceImpl::class.java) }

    override fun createOrder(createOrderCommandDTO: CreateOrderCommandDTO): CreateOrderResponseDTO =
        orderCreateCommand.createOrder(createOrderCommandDTO)

    override fun trackOrder(trackOrderQueryDTO: TrackOrderQueryDTO): TrackOrderResponseDTO =
        orderTrackCommand.trackOrder(trackOrderQueryDTO)!!
}
