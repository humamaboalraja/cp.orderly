package co.cp.orderly.payment.domain.service.mapper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.Money
import co.cp.orderly.domain.vos.OrderId
import co.cp.orderly.payment.domain.core.entity.Payment
import co.cp.orderly.payment.domain.core.events.PaymentEvent
import co.cp.orderly.payment.domain.service.consistency.model.OrderEventPayloadDTO
import co.cp.orderly.payment.domain.service.dto.PaymentServiceRequestDTO
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PaymentServiceDataMapper {
    fun paymentRequestModelToPayment(paymentServiceRequestDTO: PaymentServiceRequestDTO) =
        Payment.builder()
            .orderId(OrderId(UUID.fromString(paymentServiceRequestDTO.orderId)))
            .customerId(CustomerId(UUID.fromString(paymentServiceRequestDTO.customerId)))
            .price(Money(paymentServiceRequestDTO.price)).build()

    fun paymentEventToOrderEventPayload(paymentEvent: PaymentEvent) =
        OrderEventPayloadDTO(
            paymentEvent.payment.getId()?.getValue().toString(),
            paymentEvent.payment.customerId!!.getValue().toString(),
            paymentEvent.payment.orderId!!.getValue().toString(),
            paymentEvent.payment.price!!.getAmount(),
            paymentEvent.createdAt,
            paymentEvent.payment.paymentStatus!!.name,
            paymentEvent.errorMessages
        )
}
