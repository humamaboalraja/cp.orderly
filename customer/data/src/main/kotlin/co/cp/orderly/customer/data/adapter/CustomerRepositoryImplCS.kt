package co.cp.orderly.customer.data.adapter

import co.cp.orderly.customer.data.mapper.CustomerDataLayerDataMapper
import co.cp.orderly.customer.data.repository.CustomerPersistenceRepositoryCS
import co.cp.orderly.customer.domain.core.entity.Customer
import cp.cp.orderly.customer.domain.application.service.ports.output.repository.ICustomerApplicationServiceRepository
import org.springframework.stereotype.Component


@Component

class CustomerRepositoryImplCS(
    private val customerPersistenceRepositoryCS: CustomerPersistenceRepositoryCS,
    private val customerDataLayerDataMapper: CustomerDataLayerDataMapper
) : ICustomerApplicationServiceRepository {
    override fun createCustomer(customer: Customer): Customer =
        customerDataLayerDataMapper.customerEntityToCustomer(
            customerPersistenceRepositoryCS.save(
                customerDataLayerDataMapper.customerToCustomerEntity(customer)
            )
        )
}
