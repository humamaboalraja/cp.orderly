package co.cp.orderly.order.domain.application.service.ports.output.repository

import co.cp.orderly.order.domain.core.entity.Customer
import java.util.UUID

interface CustomerRepository {
    fun findByCustomerId(customerId: UUID): Customer?
    fun save(customer: Customer): Customer
}
