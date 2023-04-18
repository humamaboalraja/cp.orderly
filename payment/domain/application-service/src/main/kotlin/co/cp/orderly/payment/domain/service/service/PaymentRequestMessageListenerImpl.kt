package co.cp.orderly.payment.domain.service.service

import co.cp.orderly.payment.domain.service.dto.PaymentServiceRequestDTO
import co.cp.orderly.payment.domain.service.ports.input.message.listener.IPaymentRequestMessageListener
import co.cp.orderly.payment.domain.service.service.helper.PaymentApplicationServiceRequestHelper
import org.springframework.stereotype.Service

@Service
class PaymentRequestMessageListenerImpl(
    private val paymentRequestHelper: PaymentApplicationServiceRequestHelper,
) : IPaymentRequestMessageListener {

    override fun completePayment(paymentRequest: PaymentServiceRequestDTO) =
        paymentRequestHelper.persistPayment(paymentRequest)

    override fun cancelPayment(paymentRequest: PaymentServiceRequestDTO) =
        paymentRequestHelper.persistCancelPayment(paymentRequest)
}
