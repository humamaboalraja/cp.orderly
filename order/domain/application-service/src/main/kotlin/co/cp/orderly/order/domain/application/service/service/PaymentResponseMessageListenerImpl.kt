package co.cp.orderly.order.domain.application.service.service

import co.cp.orderly.order.domain.application.service.dto.message.payment.PaymentResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.payment.PaymentResponseMessageListener
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Validated
@Component
open class PaymentResponseMessageListenerImpl : PaymentResponseMessageListener {

    override fun paymentCompleted(paymentResponseDTO: PaymentResponseDTO) {
        TODO("Not yet implemented")
    }

    override fun paymentCancelled(paymentResponseDTO: PaymentResponseDTO) {
        TODO("Not yet implemented")
    }
}
