package co.cp.orderly.shop.data.consistency.mapper

import co.cp.orderly.shop.application.service.consistency.model.OrderConsistencyMessage
import co.cp.orderly.shop.data.consistency.entity.OrderConsistencyEntity
import org.springframework.stereotype.Component

@Component
class OrderConsistencyDataLayerDataMapper {
    fun orderConsistencyMessageToConsistencyEntity(
        orderConsistencyMessage: OrderConsistencyMessage
    ) =
        OrderConsistencyEntity(
            orderConsistencyMessage.id,
            orderConsistencyMessage.lltId,
            orderConsistencyMessage.createdAt,
            type = orderConsistencyMessage.type,
            payload = orderConsistencyMessage.payload,
            consistencyState = orderConsistencyMessage.consistencyState,
            approvalStatus = orderConsistencyMessage.approvalStatus,
            version = orderConsistencyMessage.version
        )

    fun orderConsistencyEntityToOrderConsistencyMessage(
        paymentConsistencyEntity: OrderConsistencyEntity
    ) =
        OrderConsistencyMessage(
            paymentConsistencyEntity.orderConsistencyId!!,
            paymentConsistencyEntity.lltId!!,
            paymentConsistencyEntity.createdAt!!,
            type = paymentConsistencyEntity.type!!,
            payload = paymentConsistencyEntity.payload!!,
            consistencyState = paymentConsistencyEntity.consistencyState!!,
            approvalStatus = paymentConsistencyEntity.approvalStatus!!,
            version = paymentConsistencyEntity.version!!,
        )
}
