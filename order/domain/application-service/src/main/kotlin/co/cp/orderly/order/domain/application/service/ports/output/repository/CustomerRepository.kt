package co.cp.orderly.order.domain.application.service.ports.output.repository

import co.cp.orderly.order.domain.core.entity.Customer
import java.util.UUID

interface CustomerRepository {
    fun findCustomerById(customerId: UUID): Customer?
}
