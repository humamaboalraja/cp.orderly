package co.cp.orderly.shop.domain.core.entity

import co.cp.orderly.domain.entity.AggregateRoot
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderApprovalStatus
import co.cp.orderly.domain.vos.OrderStatus
import co.cp.orderly.domain.vos.ShopId
import co.cp.orderly.shop.domain.core.vos.OrderApprovalId
import java.util.UUID

class Shop private constructor(
    val shopId: ShopId?,
    var orderApproval: OrderApproval?,
    var isActive: Boolean?,
    val orderData: OrderData?
) : AggregateRoot<ShopId>() {

    init {
        shopId?.let { super.setId(it) }
    }

    companion object {
        fun builder(): Builder = Builder()
    }

    data class Builder(
        var shopId: ShopId? = null,
        var orderApproval: OrderApproval? = null,
        var isActive: Boolean? = null,
        var orderData: OrderData? = null,
    ) {
        fun shopId(shopId: ShopId?) = apply { this.shopId = shopId }
        fun orderApproval(orderApproval: OrderApproval?) = apply { this.orderApproval = orderApproval }
        fun isActive(isActive: Boolean?) = apply { this.isActive = isActive }
        fun orderData(orderData: OrderData?) = apply { this.orderData = orderData }
        fun build() = Shop(
            shopId, orderApproval, isActive, orderData
        )
    }

    fun validateOrder(errorMessages: MutableList<String>) {
        when {
            orderData?.orderStatus != OrderStatus.PAID ->
                errorMessages.add("Order #${orderData?.getId()}'s payment wasn't completed")
        }
        val totalAmount = orderData?.products?.map {
            if (!it.isAvailable!!) errorMessages.add("Product #${it.getId()}'s isn't available")
            it.price?.multiplyMoney(it.quantity!!)
        }?.fold(Money.ZERO_VALUE) { acc, money -> money?.addMoney(acc)!! }
        if (totalAmount != orderData?.totalAmount)
            errorMessages.add("Order #${orderData?.getId()}'s price isn't correct")
    }

    fun startOrderApproval(orderApprovalStatus: OrderApprovalStatus) {
        this.orderApproval = OrderApproval.builder()
            .orderApprovalId(OrderApprovalId(UUID.randomUUID()))
            .shopId(this.getId())
            .orderId(this.orderData?.getId())
            .orderApprovalStatus(orderApprovalStatus)
            .build()
    }

    fun changeShopStatusTo(status: Boolean) = run { this.isActive = status }
}
