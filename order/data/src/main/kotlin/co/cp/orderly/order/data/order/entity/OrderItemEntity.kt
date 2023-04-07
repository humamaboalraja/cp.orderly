package co.cp.orderly.order.data.order.entity

import java.math.BigDecimal
import java.util.Objects
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@IdClass(OrderItemEntityId::class)
@Table(name = "order_items")
@Entity
open class OrderItemEntity(
    @Id
    open var orderItemId: Long? = null,

    @Id
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "ORDER_ID")
    open var order: OrderEntity? = null,
    open var productId: UUID? = null,
    open var price: BigDecimal? = null,
    open var quantity: Int? = null,
    open var subTotal: BigDecimal? = null
) {
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
