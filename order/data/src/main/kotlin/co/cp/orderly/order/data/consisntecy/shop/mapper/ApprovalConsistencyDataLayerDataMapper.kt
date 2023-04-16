package co.cp.orderly.order.data.consisntecy.shop.mapper

import co.cp.orderly.order.data.consisntecy.shop.entity.ApprovalConsistencyEntity
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import org.springframework.stereotype.Component

@Component
class ApprovalConsistencyDataLayerDataMapper {

    fun orderCreatedOutboxMessageToOutboxEntity(
        orderApprovalOutboxMessage: OrderApprovalConsistencyMessage
    ): ApprovalConsistencyEntity =
        ApprovalConsistencyEntity(
            orderApprovalOutboxMessage.id,
            orderApprovalOutboxMessage.lltId,
            orderApprovalOutboxMessage.createdAt,
            type = orderApprovalOutboxMessage.type,
            payload = orderApprovalOutboxMessage.payload,
            orderStatus = orderApprovalOutboxMessage.orderStatus,
            lltStatus = orderApprovalOutboxMessage.lltStatus,
            consistencyStatus = orderApprovalOutboxMessage.consistencyState,
            version = orderApprovalOutboxMessage.version!!
        )

    fun approvalOutboxEntityToOrderApprovalOutboxMessage(
        approvalOutboxEntity: ApprovalConsistencyEntity
    ): OrderApprovalConsistencyMessage =
        OrderApprovalConsistencyMessage(
            approvalOutboxEntity.id!!,
            approvalOutboxEntity.lltId!!,
            approvalOutboxEntity.createdAt!!,
            type = approvalOutboxEntity.type!!,
            payload = approvalOutboxEntity.payload!!,
            orderStatus = approvalOutboxEntity.orderStatus!!,
            lltStatus = approvalOutboxEntity.lltStatus!!,
            consistencyState = approvalOutboxEntity.consistencyStatus!!,
            version = approvalOutboxEntity.version
        )
}
