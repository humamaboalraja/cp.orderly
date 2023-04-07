package co.cp.orderly.customer.domain.core.service

import co.cp.orderly.customer.domain.core.entity.Customer
import co.cp.orderly.customer.domain.core.event.CustomerCreatedEvent

interface ICustomerCoreDomainService {
    fun validateAndStartCustomer(customer: Customer): CustomerCreatedEvent
}
