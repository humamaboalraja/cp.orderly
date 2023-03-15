package co.cp.orderly.order.domain.application.service.service.commands

import co.cp.orderly.order.domain.application.service.dto.create.order.CreateOrderCommandDTO
import co.cp.orderly.order.domain.application.service.ports.output.repository.CustomerRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.OrderRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.ShopRepository
import co.cp.orderly.order.domain.application.service.utils.dataMapper.OrderDataMapper
import co.cp.orderly.order.domain.core.entity.Order
import co.cp.orderly.order.domain.core.entity.Shop
import co.cp.orderly.order.domain.core.event.OrderCreatedEvent
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import co.cp.orderly.order.domain.core.service.IOrderDomainService
import org.jboss.logging.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
open class OrderCreateHelper(
    private val orderDomainService: IOrderDomainService,
    private val OrderRepository: OrderRepository,
    private val CustomerRepository: CustomerRepository,
    private val ShopRepository: ShopRepository,
    private val orderDataMapper: OrderDataMapper,
) {
    private val logger = Logger.getLogger(OrderCreateCommand::class.java)

    @Transactional
    open fun persistOrder(createOrderCommandDTO: CreateOrderCommandDTO): OrderCreatedEvent {
        findCustomerById(createOrderCommandDTO.customerId)
        val shop = findShopById(createOrderCommandDTO)
        val order = orderDataMapper.createOrderCommandToOrder(createOrderCommandDTO)
        val orderCreatedEvent = orderDomainService.validateAndStartOrder(order, shop)
        persistOrder(order)
        logger.info("Order ${orderCreatedEvent.order.getId()?.getValue()} has been successfully created ")
        return orderCreatedEvent
    }

    private fun findShopById(createOrderCommandDTO: CreateOrderCommandDTO): Shop {
        val shop = orderDataMapper.createOrderCommandToShop(createOrderCommandDTO)
        val shopNullable = ShopRepository.getShopDetails(shop)
        if (shopNullable == null) {
            logger.warn("Couldn't find shop #${createOrderCommandDTO.shopId}")
            throw OrderDomainException("Couldn't find shop #${createOrderCommandDTO.shopId}")
        }
        return shopNullable
    }

    private fun findCustomerById(customerId: UUID) =
        CustomerRepository.findCustomerById(customerId) ?: {
            logger.warn("Couldn't find customer #$customerId")
            throw OrderDomainException("Couldn't find customer #$customerId")
        }

    private fun persistOrder(order: Order): Order =
        when (val persistedOrder = OrderRepository.saveOrder(order)) {
            null -> {
                logger.warn("Couldn't save order")
                throw OrderDomainException("Couldn't save order")
            }
            else -> {
                logger.info("Order #${persistedOrder.getId()?.getValue()} has been persisted")
                persistedOrder
            }
        }
}
