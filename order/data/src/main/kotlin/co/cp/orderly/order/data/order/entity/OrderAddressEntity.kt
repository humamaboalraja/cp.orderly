package co.cp.orderly.order.data.order.entity

import java.util.Objects
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "order_address")
open class OrderAddressEntity(
    @Id
    open val orderAddressId: UUID? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "ORDER_ID")
    open var order: OrderEntity? = null,
    open var street: String? = null,
    open var postalCode: String? = null,
    open var city: String? = null
) {

    constructor() : this(null, null, null, null)

    override fun equals(other: Any?): Boolean {
        other as OrderAddressEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> orderAddressId == other.orderAddressId
        }
    }

    override fun hashCode(): Int = Objects.hash(orderAddressId)
}
