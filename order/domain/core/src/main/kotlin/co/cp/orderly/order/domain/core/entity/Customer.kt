package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.AggregateRoot
import co.cp.orderly.domain.vos.CustomerId

class Customer(
    customerId: CustomerId?,
    val username: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?
) : AggregateRoot<CustomerId>() {
    init { if (customerId != null) { super.setId(customerId) } }
    constructor() : this(null, null, null, null, null)

    constructor(customerId: CustomerId?) : this() {
        super.setId(customerId!!)
    }
}
