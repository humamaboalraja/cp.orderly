package co.cp.orderly.customer.domain.core.entity

import co.cp.orderly.domain.entity.AggregateRoot
import co.cp.orderly.domain.vos.CustomerId

class Customer(
    customerId: CustomerId,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
) : AggregateRoot<CustomerId>() { init { super.setId(customerId) } }
