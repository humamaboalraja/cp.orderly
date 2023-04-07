package co.cp.orderly.customer.data.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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
