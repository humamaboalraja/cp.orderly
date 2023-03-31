package co.cp.orderly.payment.domain.service.ports.input.message.listener

import co.cp.orderly.payment.domain.service.dto.PaymentServiceRequestDTO

interface IPaymentRequestMessageListener {
    fun completePayment(paymentRequest: PaymentServiceRequestDTO)
    fun cancelPayment(paymentRequest: PaymentServiceRequestDTO)
}
