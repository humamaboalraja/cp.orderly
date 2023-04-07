package co.cp.orderly.order.data.customer.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.util.UUID

@Entity
@Table(name = "customer_materialized_view", schema = "customer")
open class CustomerEntity(
    @Id
    open var customerId: UUID?

) { constructor() : this(null) }
