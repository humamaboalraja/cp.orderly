package cp.cp.orderly.customer.domain.application.service.service.customer

import co.cp.orderly.customer.domain.core.event.CustomerCreatedEvent
import co.cp.orderly.customer.domain.core.exception.CustomerDomainException
import co.cp.orderly.customer.domain.core.service.ICustomerCoreDomainService
import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerCommandDTO
import cp.cp.orderly.customer.domain.application.service.mapper.CustomerApplicationServiceDataMapper
import cp.cp.orderly.customer.domain.application.service.ports.output.repository.ICustomerApplicationServiceRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Component
open class CreateCustomerCommandHandler(
    private val customerDomainService: ICustomerCoreDomainService,
    private val customerRepository: ICustomerApplicationServiceRepository,
    private val customerApplicationServiceDataMapper: CustomerApplicationServiceDataMapper,
) {
    companion object { private val logger = Logger.getLogger(CreateCustomerCommandHandler::class.java.name) }

    @Transactional
    open fun createCustomer(createCustomerCommand: CreateCustomerCommandDTO): CustomerCreatedEvent {
        val customer = customerApplicationServiceDataMapper.convertCustomerCommandToCustomer(createCustomerCommand)
        val customerCreatedEvent = customerDomainService.validateAndStartCustomer(customer)
        val savedCustomer = customerRepository.createCustomer(customer)
        if (savedCustomer == null) {
            logger.info("Could not save customer #${createCustomerCommand.customerId}")
            throw CustomerDomainException("Could not save customer with id #${createCustomerCommand.customerId}")
        }
        logger.info("Returning CustomerCreatedEvent for customer #${createCustomerCommand.customerId}")
        return customerCreatedEvent
    }
}
