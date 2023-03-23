package co.cp.orderly.order.application.controller

import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderQueryDTO
import co.cp.orderly.order.domain.application.service.dto.track.order.TrackOrderResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.service.OrderApplicationService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.logging.Logger

@RestController
@RequestMapping(value = ["/orders/"], produces = [MediaType.APPLICATION_JSON_VALUE])
class OrderController(
    private val orderApplicationService: OrderApplicationService
) {
    companion object { private val logger = Logger.getLogger(OrderController::class.java.name) }

    @PostMapping
    fun createOrder(@RequestBody createOrderCommandDTO: CreateOrderCommandDTO): ResponseEntity<CreateOrderResponseDTO> {
        logger.info(
            "Shop: ${createOrderCommandDTO.shopId} - " +
                "Creating order for customer #${createOrderCommandDTO.customerId}"
        )
        val createdOrderResponseDTO = orderApplicationService.createOrder(createOrderCommandDTO)
        logger.info("Order ${createdOrderResponseDTO.orderTrackingId} has been created")
        return ResponseEntity.ok(createdOrderResponseDTO)
    }

    @GetMapping("/{trackingId}")
    fun getOrderByTrackingId(@PathVariable trackingId: UUID): ResponseEntity<TrackOrderResponseDTO> {
        val trackOrderResponseDTO = orderApplicationService.trackOrder(TrackOrderQueryDTO(trackingId))
        logger.info("Getting order status of tracking id #${trackOrderResponseDTO.orderTrackingId}")
        return ResponseEntity.ok(trackOrderResponseDTO)
    }
}
