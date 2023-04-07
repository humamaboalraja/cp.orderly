package co.cp.orderly.order.data.customer.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "customer_materialized_view", schema = "customer")
open class CustomerEntity(
    @Id
    open var customerId: UUID?

) { constructor() : this(null) }
