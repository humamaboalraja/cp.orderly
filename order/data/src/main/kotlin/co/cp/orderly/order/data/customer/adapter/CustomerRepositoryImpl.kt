package co.cp.orderly.order.data.customer.adapter

import co.cp.orderly.order.data.customer.mapper.CustomerDataMapper
import co.cp.orderly.order.data.customer.repository.CustomerPersistenceRepository
import co.cp.orderly.order.domain.application.service.ports.output.repository.CustomerRepository
import co.cp.orderly.order.domain.core.entity.Customer
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomerRepositoryImpl(
    private val customerDataMapper: CustomerDataMapper,
    private val customerRepository: CustomerPersistenceRepository
) : CustomerRepository {

    override fun findByCustomerId(customerId: UUID) =
        customerRepository.findById(customerId).map(customerDataMapper::customerEntityToCustomer).get()

    override fun save(customer: Customer) = customerDataMapper.customerEntityToCustomer(
        customerRepository.save(customerDataMapper.customerToCustomerEntity(customer))
    )


}
