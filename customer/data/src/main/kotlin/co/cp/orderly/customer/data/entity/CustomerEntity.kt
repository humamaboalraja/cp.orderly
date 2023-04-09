package co.cp.orderly.customer.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "customers")
open class CustomerEntity(
    @Id
    open val customerId: UUID? = null,
    open var username: String? = null,
    open var email: String? = null,
    open var firstName: String? = null,
    open var lastName: String? = null
) {

    constructor() : this(null, null, null, null)
}
