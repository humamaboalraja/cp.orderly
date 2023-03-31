package co.cp.orderly.payment.domain.core.entity

import co.cp.orderly.domain.entity.AggregateRoot
import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.payment.domain.core.vos.PaymentId
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

class Payment private constructor(
    val orderId: OrderId?,
    val customerId: CustomerId?,
    val price: Money?,
    var paymentStatus: PaymentStatus?,
    var createdAt: ZonedDateTime?
) : AggregateRoot<PaymentId>() {

    init { builder().paymentId?.let { super.setId(it) } }

    companion object { fun builder(): Builder = Builder() }

    data class Builder(
        var paymentId: PaymentId? = null,
        var orderId: OrderId? = null,
        var customerId: CustomerId? = null,
        var price: Money? = null,
        var paymentStatus: PaymentStatus? = null,
        var createdAt: ZonedDateTime? = null
    ) {
        fun paymentId(paymentId: PaymentId?) = apply { this.paymentId = paymentId }
        fun orderId(orderId: OrderId?) = apply { this.orderId = orderId }
        fun customerId(customerId: CustomerId?) = apply { this.customerId = customerId }
        fun price(price: Money?) = apply { this.price = price }
        fun paymentStatus(paymentStatus: PaymentStatus?) = apply { this.paymentStatus = paymentStatus }
        fun createdAt(createdAt: ZonedDateTime) = apply { this.createdAt = createdAt }
        fun build() = Payment(orderId, customerId, price, paymentStatus, createdAt)
    }

    fun startPayment() {
        setId(PaymentId(UUID.randomUUID()))
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }

    fun validatePayment(errorMessages: MutableList<String>) {
        when {
            price == null || !price.isAmountGreaterThanZero() ->
                errorMessages.add("Total price must be greater than 0!")
        }
    }

    fun updateStatus(paymentStatus: PaymentStatus?) = run { this.paymentStatus = paymentStatus }
}
