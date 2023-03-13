package co.cp.orderly.order.domain.application.service.ports.input.service

import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderQueryDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderResponseDTO
import javax.validation.Valid

interface OrderApplicationService {

    fun createOrder(@Valid createOrderCommandDTO: CreateOrderCommandDTO): CreateOrderResponseDTO
    fun trackOrder(@Valid trackOrderQueryDTO: TrackOrderQueryDTO): TrackOrderResponseDTO
}
