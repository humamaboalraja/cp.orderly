package co.cp.orderly.payment.data.payment.mapper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.payment.data.payment.entity.PaymentEntity
import co.cp.orderly.payment.domain.core.entity.Payment
import co.cp.orderly.payment.domain.core.vos.PaymentId
import org.springframework.stereotype.Component

@Component
class PaymentDataMapper {

    fun mapPaymentEntityToPayment(paymentEntity: PaymentEntity): Payment =
        Payment.builder()
            .paymentId(PaymentId(paymentEntity.paymentId!!))
            .customerId(CustomerId(paymentEntity.customerId!!))
            .orderId(OrderId(paymentEntity.orderId!!))
            .price(Money(paymentEntity.price!!))
            .createdAt(paymentEntity.createdAt!!)
            .build()

    fun mapPaymentToPaymentEntity(payment: Payment): PaymentEntity =
        PaymentEntity(
            payment.getId()?.getValue(),
            payment.customerId?.getValue(),
            payment.orderId?.getValue(),
            payment.price?.getAmount(),
            payment.paymentStatus,
            payment.createdAt
        )
}
