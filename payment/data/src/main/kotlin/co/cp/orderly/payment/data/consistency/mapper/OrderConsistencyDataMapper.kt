package co.cp.orderly.payment.data.consistency.mapper

import co.cp.orderly.payment.data.consistency.entity.OrderConsistencyEntity
import co.cp.orderly.payment.domain.service.consistency.model.OrderConsistencyMessage
import org.springframework.stereotype.Component

@Component
class OrderConsistencyDataMapper {

    fun orderConsistencyMessageToConsistencyEntity(orderConsistencyMessage: OrderConsistencyMessage) =
        OrderConsistencyEntity(
            orderConsistencyMessage.id,
            orderConsistencyMessage.lltId,
            orderConsistencyMessage.createdAt,
            type = orderConsistencyMessage.type,
            payload = orderConsistencyMessage.payload,
            consistencyStatus = orderConsistencyMessage.consistencyStatus,
            paymentStatus = orderConsistencyMessage.paymentStatus,
            version = orderConsistencyMessage.version
        )

    fun orderConsistencyEntityToOrderConsistencyMessage(paymentConsistencyEntity: OrderConsistencyEntity) =
        OrderConsistencyMessage(
            paymentConsistencyEntity.orderConsistencyId!!,
            paymentConsistencyEntity.lltId!!,
            paymentConsistencyEntity.createdAt!!,
            type = paymentConsistencyEntity.type!!,
            payload = paymentConsistencyEntity.payload!!,
            consistencyStatus = paymentConsistencyEntity.consistencyStatus!!,
            paymentStatus = paymentConsistencyEntity.paymentStatus!!,
            version = paymentConsistencyEntity.version!!
        )
}
