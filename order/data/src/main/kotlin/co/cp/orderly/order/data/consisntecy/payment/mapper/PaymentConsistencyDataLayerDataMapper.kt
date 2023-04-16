package co.cp.orderly.order.data.consisntecy.payment.mapper

import co.cp.orderly.order.data.consisntecy.payment.entity.PaymentConsistencyEntity
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import org.springframework.stereotype.Component

@Component
class PaymentConsistencyDataLayerDataMapper {
    fun orderPaymentConsistencyMessageToConsistencyEntity(
        orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage
    ): PaymentConsistencyEntity =
        PaymentConsistencyEntity(
            orderPaymentConsistencyMessage.id,
            orderPaymentConsistencyMessage.lltId,
            orderPaymentConsistencyMessage.createdAt,
            type = orderPaymentConsistencyMessage.type,
            payload = orderPaymentConsistencyMessage.payload,
            orderStatus = orderPaymentConsistencyMessage.orderStatus,
            lltStatus = orderPaymentConsistencyMessage.lltStatus,
            consistencyState = orderPaymentConsistencyMessage.consistencyState,
            version = orderPaymentConsistencyMessage.version
        )

    fun paymentConsistencyEntityToOrderPaymentConsistencyMessage(
        paymentConsistencyEntity: PaymentConsistencyEntity
    ): OrderPaymentConsistencyMessage =
        OrderPaymentConsistencyMessage(
            paymentConsistencyEntity.id!!,
            paymentConsistencyEntity.lltId!!,
            paymentConsistencyEntity.createdAt!!,
            type = paymentConsistencyEntity.type!!,
            payload = paymentConsistencyEntity.payload!!,
            orderStatus = paymentConsistencyEntity.orderStatus!!,
            lltStatus = paymentConsistencyEntity.lltStatus!!,
            consistencyState = paymentConsistencyEntity.consistencyState!!,
            version = paymentConsistencyEntity.version,
        )
}
