package co.cp.orderly.order.data.order.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.Objects
import java.util.UUID

@Entity
@IdClass(OrderItemEntityId::class)
@Table(name = "order_items")
open class OrderItemEntity(
    @Id
    open var orderItemId: Long?,

    @Id
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "ORDER_ID")
    open var order: OrderEntity?,

    open var productId: UUID?,
    open var price: BigDecimal?,
    open var quantity: Int?,
    open var subTotal: BigDecimal?
) {

    constructor() : this(null, null, null, null, null, null)
    override fun equals(other: Any?): Boolean {
        other as OrderItemEntity
        return when {
            this === other -> true
            javaClass != other.javaClass -> false
            else -> orderItemId == other.orderItemId && order == other.order
        }
    }

    override fun hashCode(): Int = Objects.hash(orderItemId, order)
}
