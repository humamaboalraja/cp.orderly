package co.cp.orderly.order.domain.application.service.service

import co.cp.orderly.order.domain.application.service.dto.message.payment.PaymentResponseDTO
import co.cp.orderly.order.domain.application.service.ports.input.message.listener.payment.PaymentResponseMessageListener
import co.cp.orderly.order.domain.application.service.service.commands.OrderCreateCommand
import org.jboss.logging.Logger
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Validated
@Component
class PaymentResponseMessageListenerImpl : PaymentResponseMessageListener {

    private val logger = Logger.getLogger(OrderCreateCommand::class.java)

    override fun paymentCompleted(paymentResponseDTO: PaymentResponseDTO) {
        TODO("Not yet implemented")
    }

    override fun paymentCancelled(paymentResponseDTO: PaymentResponseDTO) {
        TODO("Not yet implemented")
    }
}
