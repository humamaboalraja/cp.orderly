package co.cp.orderly.order.domain.application.service.service.message.customer

import co.cp.orderly.order.domain.application.service.dto.message.customer.CustomerModelDTO
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.customer.CustomerMessageListener
import co.cp.orderly.order.domain.application.service.ports.output.repository.CustomerRepository
import co.cp.orderly.order.domain.application.service.utils.dataMapper.OrderApplicationServiceDataMapper
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class CustomerMessageListenerImpl(
    private val customerRepository: CustomerRepository,
    private val orderDataMapper: OrderApplicationServiceDataMapper
) : CustomerMessageListener {

    companion object { private val logger = Logger.getLogger(CustomerMessageListenerImpl::class.java.name) }

    override fun customerCreated(customerModel: CustomerModelDTO) {
        val customer = customerRepository.save(orderDataMapper.customerModelToCustomer(customerModel))
        if (customer == null) {
            logger.info("Customer #${customerModel.id} couldn't be created in order database")
            throw OrderDomainException("Customer #${customerModel.id} couldn't be created in order database")
        }
        logger.info("Customer #${customer.getId()} has been persisted in the order database successfully ")
    }
}
