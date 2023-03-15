package co.cp.orderly.order.domain.application.service.service.commands

import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import co.cp.orderly.order.domain.application.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import co.cp.orderly.order.domain.application.service.utils.dataMapper.OrderDataMapper
import org.jboss.logging.Logger
import org.springframework.stereotype.Component

@Component
open class OrderCreateCommand(
    private val orderCreateHelper: OrderCreateHelper,
    private val orderDataMapper: OrderDataMapper,
    private val OrderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {

    private val logger = Logger.getLogger(OrderCreateCommand::class.java)

    open fun createOrder(createOrderCommandDTO: CreateOrderCommandDTO): CreateOrderResponseDTO {
        val orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommandDTO)
        logger.info("Order #${orderCreatedEvent.order.getId()?.getValue()} has been created")
        OrderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent)
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.order)
    }
}
