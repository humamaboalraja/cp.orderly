package co.cp.orderly.order.messaging.mapper

import co.cp.orderly.domain.vos.OrderApprovalStatus
import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.infrastructure.kafka.model.avro.CustomerAvroModel
import co.cp.orderly.infrastructure.kafka.model.avro.PaymentOrderStatus
import co.cp.orderly.infrastructure.kafka.model.avro.PaymentRequestAvroModel
import co.cp.orderly.infrastructure.kafka.model.avro.PaymentResponseAvroModel
import co.cp.orderly.infrastructure.kafka.model.avro.Product
import co.cp.orderly.infrastructure.kafka.model.avro.ShopApprovalRequestAvroModel
import co.cp.orderly.infrastructure.kafka.model.avro.ShopApprovalResponseAvroModel
import co.cp.orderly.infrastructure.kafka.model.avro.ShopOrderStatus
import co.cp.orderly.order.domain.application.service.consistency.model.approval.OrderApprovalEventDTO
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentEventDTO
import co.cp.orderly.order.domain.application.service.dto.message.customer.CustomerModelDTO
import co.cp.orderly.order.domain.application.service.dto.message.payment.PaymentResponseDTO
import co.cp.orderly.order.domain.application.service.dto.message.shop.ShopApprovalResponseDTO
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderMessagingLayerDateMapper {
    fun paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel: PaymentResponseAvroModel) =
        PaymentResponseDTO(
            paymentResponseAvroModel.id,
            paymentResponseAvroModel.lltId,
            paymentResponseAvroModel.paymentId,
            paymentResponseAvroModel.customerId,
            paymentResponseAvroModel.orderId,
            paymentResponseAvroModel.price,
            paymentResponseAvroModel.createdAt,
            PaymentStatus.valueOf(paymentResponseAvroModel.paymentStatus.name),
            paymentResponseAvroModel.errorMessages
        )

    fun orderPaymentEventToPaymentRequestAvroModel(lltId: String?, orderPaymentEventPayload: OrderPaymentEventDTO) =
        PaymentRequestAvroModel(
            UUID.randomUUID().toString(),
            lltId,
            orderPaymentEventPayload.customerId,
            orderPaymentEventPayload.orderId,
            orderPaymentEventPayload.price,
            orderPaymentEventPayload.createdAt.toInstant(),
            PaymentOrderStatus.valueOf(orderPaymentEventPayload.paymentOrderStatus)
        )

    fun orderApprovalEventToShopApprovalRequestAvroModel(
        lltId: String?,
        orderApprovalEventPayload: OrderApprovalEventDTO
    ) = ShopApprovalRequestAvroModel(
        UUID.randomUUID().toString(),
        lltId,
        orderApprovalEventPayload.orderId,
        orderApprovalEventPayload.shopId,
        ShopOrderStatus.valueOf(orderApprovalEventPayload.shopOrderStatus),
        orderApprovalEventPayload.products.map { Product(it.id.toString(), it.quantity) }.toList(),
        orderApprovalEventPayload.price,
        orderApprovalEventPayload.createdAt.toInstant(),
    )

    fun customerAvroModelToCustomerModel(customerAvroModel: CustomerAvroModel) =
        CustomerModelDTO(
            customerAvroModel.id,
            customerAvroModel.username,
            customerAvroModel.email,
            customerAvroModel.firstName,
            customerAvroModel.lastName
        )

    fun approvalResponseAvroModelToApprovalResponse(shopApprovalResponseAvroModel: ShopApprovalResponseAvroModel) =
        ShopApprovalResponseDTO(
            shopApprovalResponseAvroModel.getId(),
            shopApprovalResponseAvroModel.getLltId(),
            shopApprovalResponseAvroModel.shopId,
            shopApprovalResponseAvroModel.orderId,
            shopApprovalResponseAvroModel.createdAt,
            OrderApprovalStatus.valueOf(shopApprovalResponseAvroModel.orderApprovalStatus.name),
            shopApprovalResponseAvroModel.errorMessages
        )
}
