package co.cp.orderly.order.domain.core.entity

import co.cp.orderly.domain.entity.AggregateRoot
import co.cp.orderly.domain.vos.CustomerId

class Customer(
    customerId: CustomerId?
) : AggregateRoot<CustomerId>() {
    init { if (customerId != null) { super.setId(customerId) } }
    constructor() : this(null)
}
