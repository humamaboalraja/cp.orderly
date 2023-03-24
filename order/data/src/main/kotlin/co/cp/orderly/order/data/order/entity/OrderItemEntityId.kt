package co.cp.orderly.order.data.order.entity

import java.io.Serializable

data class OrderItemEntityId(val id: Long? = null, val order: OrderEntity? = null) : Serializable
