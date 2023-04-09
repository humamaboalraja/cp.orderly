package co.cp.orderly.customer.data.mapper

import co.cp.orderly.customer.data.entity.CustomerEntity
import co.cp.orderly.customer.domain.core.entity.Customer
import co.cp.orderly.domain.vos.CustomerId
import org.springframework.stereotype.Component

@Component
class CustomerDataLayerDataMapper {
    fun customerEntityToCustomer(customerEntity: CustomerEntity): Customer =
        Customer(
            CustomerId(customerEntity.customerId!!),
            customerEntity.username!!,
            customerEntity.firstName!!,
            customerEntity.lastName!!
        )

    fun customerToCustomerEntity(customer: Customer): CustomerEntity =
        CustomerEntity(
            customer.getId()?.getValue(), customer.username, customer.firstName, customer.lastName,
        )
}
