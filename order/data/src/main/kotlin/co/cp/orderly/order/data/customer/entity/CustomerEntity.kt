package co.cp.orderly.order.data.customer.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "customer")
open class CustomerEntity(
    @Id
    open val customerId: UUID? = null,
    open var username: String? = null,
    open var email: String? = null,
    open var firstName: String? = null,
    open var lastName: String? = null

)
