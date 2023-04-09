package co.cp.orderly.order.data.order.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.Objects
import java.util.UUID

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
