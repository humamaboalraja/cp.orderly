package co.cp.orderly.order.data.customer.mapper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.order.data.customer.entity.CustomerEntity
import co.cp.orderly.order.domain.core.entity.Customer
import org.springframework.stereotype.Component

@Component
class CustomerDataMapper {
    fun customerEntityToCustomer(customerEntity: CustomerEntity): Customer =
        Customer(CustomerId(customerEntity.customerId!!))
}
