package co.cp.orderly.order.domain.application.service.service.message.payment

import co.cp.orderly.order.domain.application.service.dto.message.payment.PaymentResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.payment.PaymentResponseMessageListener
import co.cp.orderly.order.domain.application.service.service.llt.order.OrderPaymentLlt
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.util.logging.Logger

@Validated
@Component
open class PaymentResponseMessageListenerImpl(
    private val orderPaymentLlt: OrderPaymentLlt
) : PaymentResponseMessageListener {

    private val logger = Logger.getLogger(PaymentResponseMessageListenerImpl::class.java.name)

    override fun paymentCompleted(paymentResponseDTO: PaymentResponseDTO) {
        orderPaymentLlt.process(paymentResponseDTO)
        logger.info("Order Payment's Llt process operation is completed for order #${paymentResponseDTO.orderId}")
    }

    override fun paymentCancelled(paymentResponseDTO: PaymentResponseDTO) {
        orderPaymentLlt.rollback(paymentResponseDTO)
        logger.info(
            "Order #${paymentResponseDTO.orderId} has been roll-backed " +
                "with the following messages: #${paymentResponseDTO.errorMessages.joinToString { "," }}"
        )
    }
}
