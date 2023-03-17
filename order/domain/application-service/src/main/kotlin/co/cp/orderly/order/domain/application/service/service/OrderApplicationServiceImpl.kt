package co.cp.orderly.order.domain.application.service.service

import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderQueryDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.service.OrderApplicationService
import co.cp.orderly.order.domain.application.service.service.commands.OrderCreateCommand
import co.cp.orderly.order.domain.application.service.service.commands.OrderTrackCommand
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
open class OrderApplicationServiceImpl(
    private val orderCreateCommand: OrderCreateCommand,
    private val orderTrackCommand: OrderTrackCommand,
) : OrderApplicationService {

    override fun createOrder(createOrderCommandDTO: CreateOrderCommandDTO): CreateOrderResponseDTO =
        orderCreateCommand.createOrder(createOrderCommandDTO)

    override fun trackOrder(trackOrderQueryDTO: TrackOrderQueryDTO): TrackOrderResponseDTO =
        orderTrackCommand.trackOrder(trackOrderQueryDTO)!!
}
