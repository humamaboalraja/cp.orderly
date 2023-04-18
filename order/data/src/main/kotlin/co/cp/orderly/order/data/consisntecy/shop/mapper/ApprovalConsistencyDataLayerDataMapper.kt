package co.cp.orderly.order.data.consisntecy.shop.mapper

import co.cp.orderly.order.data.consisntecy.shop.entity.ApprovalConsistencyEntity
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalConsistencyMessage
import org.springframework.stereotype.Component

@Component
class ApprovalConsistencyDataLayerDataMapper {

    fun orderCreatedConsistencyMessageToConsistencyEntity(
        orderApprovalConsistencyMessage: OrderApprovalConsistencyMessage
    ): ApprovalConsistencyEntity =
        ApprovalConsistencyEntity(
            orderApprovalConsistencyMessage.id,
            orderApprovalConsistencyMessage.lltId,
            orderApprovalConsistencyMessage.createdAt,
            type = orderApprovalConsistencyMessage.type,
            payload = orderApprovalConsistencyMessage.payload,
            orderStatus = orderApprovalConsistencyMessage.orderStatus,
            lltStatus = orderApprovalConsistencyMessage.lltStatus,
            consistencyState = orderApprovalConsistencyMessage.consistencyState,
            version = orderApprovalConsistencyMessage.version!!
        )

    fun approvalConsistencyEntityToOrderApprovalConsistencyMessage(
        approvalConsistencyEntity: ApprovalConsistencyEntity
    ): OrderApprovalConsistencyMessage =
        OrderApprovalConsistencyMessage(
            approvalConsistencyEntity.id!!,
            approvalConsistencyEntity.lltId!!,
            approvalConsistencyEntity.createdAt!!,
            type = approvalConsistencyEntity.type!!,
            payload = approvalConsistencyEntity.payload!!,
            orderStatus = approvalConsistencyEntity.orderStatus!!,
            lltStatus = approvalConsistencyEntity.lltStatus!!,
            consistencyState = approvalConsistencyEntity.consistencyState!!,
            version = approvalConsistencyEntity.version
        )
}
