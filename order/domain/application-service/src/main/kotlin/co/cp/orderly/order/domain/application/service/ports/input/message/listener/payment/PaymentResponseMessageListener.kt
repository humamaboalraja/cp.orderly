package co.cp.orderly.order.domain.application.service.ports.input.message.listener.payment

import co.cp.orderly.order.domain.application.service.dto.message.payment.PaymentResponseDTO

interface PaymentResponseMessageListener {

    fun paymentCompleted(paymentResponseDTO: PaymentResponseDTO)
    fun paymentCancelled(paymentResponseDTO: PaymentResponseDTO)
}
