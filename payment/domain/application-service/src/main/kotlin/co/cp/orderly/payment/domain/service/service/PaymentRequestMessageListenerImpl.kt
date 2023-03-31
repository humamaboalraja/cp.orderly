package co.cp.orderly.payment.domain.service.service

import co.cp.orderly.payment.domain.core.events.PaymentEvent
import co.cp.orderly.payment.domain.service.dto.PaymentServiceRequestDTO
import co.cp.orderly.payment.domain.service.ports.input.message.listener.IPaymentRequestMessageListener
import co.cp.orderly.payment.domain.service.service.helper.PaymentApplicationServiceRequestHelper
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PaymentRequestMessageListenerImpl(
    private val paymentRequestHelper: PaymentApplicationServiceRequestHelper,
) : IPaymentRequestMessageListener {

    companion object { private val logger = Logger.getLogger(PaymentRequestMessageListenerImpl::class.java.name) }

    private fun fireEvent(paymentEvent: PaymentEvent) {
        logger.info(
            "Publishing Payment event for Order #${paymentEvent.payment.orderId?.getValue()} - " +
                "Payment #${paymentEvent.payment.getId()?.getValue()}"
        )
        paymentEvent.fire()
    }

    override fun completePayment(paymentRequest: PaymentServiceRequestDTO) {
        val paymentEvent = paymentRequestHelper.persistPayment(paymentRequest)
        fireEvent(paymentEvent)
    }

    override fun cancelPayment(paymentRequest: PaymentServiceRequestDTO) {
        val paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest)
        fireEvent(paymentEvent)
    }
}
