package co.cp.orderly.customer.domain.core.service

import co.cp.orderly.customer.domain.core.entity.Customer
import co.cp.orderly.customer.domain.core.event.CustomerCreatedEvent
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.logging.Logger

class CustomerDomainServiceImpl : ICustomerCoreDomainService {
    companion object { private val logger = Logger.getLogger(CustomerDomainServiceImpl::class.java.name) }

    override fun validateAndStartCustomer(customer: Customer): CustomerCreatedEvent {
        logger.info("Customer #${customer.getId()?.getValue()} is created")
        return CustomerCreatedEvent(customer, ZonedDateTime.now(ZoneId.of("UTC")))
    }
}
