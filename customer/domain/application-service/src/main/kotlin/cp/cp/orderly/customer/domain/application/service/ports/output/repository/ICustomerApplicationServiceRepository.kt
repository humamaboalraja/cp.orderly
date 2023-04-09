package cp.cp.orderly.customer.domain.application.service.ports.output.repository

import co.cp.orderly.customer.domain.core.entity.Customer

interface ICustomerApplicationServiceRepository {
    fun createCustomer(customer: Customer): Customer
}
