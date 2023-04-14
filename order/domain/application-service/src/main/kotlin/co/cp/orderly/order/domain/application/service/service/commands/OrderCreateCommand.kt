package co.cp.orderly.order.domain.application.service.service.commands

import co.cp.orderly.order.domain.application.service.consistency.scheduler.payment.PaymentConsistencyUtil
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderResponseDTO
import co.cp.orderly.order.domain.application.service.ports.output.repository.CustomerRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.ShopRepository
import co.cp.orderly.order.domain.application.service.service.llt.order.OrderLltUtil
import co.cp.orderly.order.domain.application.service.utils.dataMapper.OrderApplicationServiceDataMapper
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.Shop
import co.cp.orderly.order.domain.core.event.OrderCreatedEvent
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import co.cp.orderly.order.domain.core.service.IOrderDomainService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.logging.Logger

@Component
open class OrderCreateCommand(
    private val orderDomainService: IOrderDomainService,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val shopRepository: ShopRepository,
    private val orderApplicationServiceDataMapper: OrderApplicationServiceDataMapper,
    private val paymentConsistencyUtil: PaymentConsistencyUtil,
    private val orderLltUtil: OrderLltUtil
) {

    companion object { private val logger = Logger.getLogger(OrderCreateCommand::class.java.name) }

    @Transactional
    open fun createOrder(createOrderCommandDTO: CreateOrderCommandDTO): CreateOrderResponseDTO {
        val orderCreatedEvent = persistOrder(createOrderCommandDTO)
        logger.info("Order  has been created")

        val createdOrderResponse = orderApplicationServiceDataMapper.orderToCreateOrderResponse(
            orderCreatedEvent.order, "Order has been successfully created"
        )

        paymentConsistencyUtil.savePaymentConsistencyMessage(
            orderApplicationServiceDataMapper.orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent),
            orderCreatedEvent.order.orderStatus!!,
            orderLltUtil.orderStatusToLltStatus(orderCreatedEvent.order.orderStatus),
            ConsistencyState.STARTED,
            UUID.randomUUID()
        )

        logger.info("Returning CreateOrderResponse with order #${orderCreatedEvent.order.getId()}")

        return createdOrderResponse
    }

    @Transactional
    open fun persistOrder(createOrderCommandDTO: CreateOrderCommandDTO): OrderCreatedEvent {
        findByCustomerId(createOrderCommandDTO.customerId)
        val shop = findShopById(createOrderCommandDTO)
        val order = orderApplicationServiceDataMapper.createOrderCommandToOrder(createOrderCommandDTO)
        val orderCreatedEvent = orderDomainService.validateAndStartOrder(order, shop)
        saveOrder(order)
        logger.info(
            "Order ${orderCreatedEvent.order.getId()?.getValue()} has been successfully created "
        )
        return orderCreatedEvent
    }

    private fun findShopById(createOrderCommandDTO: CreateOrderCommandDTO): Shop {
        val shop = orderApplicationServiceDataMapper.createOrderCommandToShop(createOrderCommandDTO)
        val shopNullable = shopRepository.getShopDetails(shop)
        if (shopNullable == null) {
            logger.warning("Couldn't find shop #${createOrderCommandDTO.shopId}")
            throw OrderDomainException("Couldn't find shop #${createOrderCommandDTO.shopId}")
        }
        return shopNullable
    }

    private fun findByCustomerId(customerId: UUID) =
        customerRepository.findByCustomerId(customerId)
            ?: {
                logger.warning("Couldn't find customer #$customerId")
                throw OrderDomainException("Couldn't find customer #$customerId")
            }

    private fun saveOrder(order: Order): Order {
        when (val persistedOrder = orderRepository.saveOrder(order)) {
            null -> {
                logger.warning("Couldn't save order")
                throw OrderDomainException("Couldn't save order")
            }
            else -> {
                logger.info("Order #${persistedOrder.getId()?.getValue()} has been persisted")
                return persistedOrder
            }
        }
    }
}
