package co.cp.orderly.order.data.order.entity

import co.cp.orderly.domain.vos.OrderStatus
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "orders")
open class OrderEntity(
    @Id
    open var orderId: UUID?,
    open var customerId: UUID?,
    open var shopId: UUID?,
    open var trackingId: UUID?,
    open var price: BigDecimal?,

    @Enumerated(EnumType.STRING)
    open var orderStatus: OrderStatus?,
    open var errorMessages: String?,

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL])
    open var address: OrderAddressEntity?,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    open val items: List<OrderItemEntity>?
) {
    constructor() : this(
        null, null, null, null,
        null, null, null, null, null
    )

    override fun equals(other: Any?): Boolean {
        other as OrderEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> orderId == other.orderId
        }
    }

    override fun hashCode(): Int = orderId.hashCode()
}
