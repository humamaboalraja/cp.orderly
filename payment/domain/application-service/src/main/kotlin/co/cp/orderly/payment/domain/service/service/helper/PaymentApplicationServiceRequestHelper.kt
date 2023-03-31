package co.cp.orderly.payment.domain.service.service.helper

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.payment.domain.core.IPaymentDomainService
import co.cp.orderly.payment.domain.core.entity.CreditEntry
import co.cp.orderly.payment.domain.core.entity.CreditHistory
import co.cp.orderly.payment.domain.core.entity.Payment
import co.cp.orderly.payment.domain.core.events.PaymentEvent
import co.cp.orderly.payment.domain.service.dto.PaymentServiceRequestDTO
import co.cp.orderly.payment.domain.service.exception.PaymentApplicationServiceException
import co.cp.orderly.payment.domain.service.mapper.PaymentServiceDataMapper
import co.cp.orderly.payment.domain.service.ports.outptut.message.publisher.IPaymentCanceledMessagePublisher
import co.cp.orderly.payment.domain.service.ports.outptut.message.publisher.IPaymentCompletedMessagePublisher
import co.cp.orderly.payment.domain.service.ports.outptut.message.publisher.IPaymentFailedMessagePublisher
import co.cp.orderly.payment.domain.service.ports.outptut.repository.ICreditEntryRepository
import co.cp.orderly.payment.domain.service.ports.outptut.repository.ICreditHistoryRepository
import co.cp.orderly.payment.domain.service.ports.outptut.repository.IPaymentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.logging.Logger

@Component
open class PaymentApplicationServiceRequestHelper(
    private val paymentDomainService: IPaymentDomainService,
    private val paymentServiceDataMapper: PaymentServiceDataMapper,
    private val paymentRepository: IPaymentRepository,
    private val creditEntryRepository: ICreditEntryRepository,
    private val creditHistoryRepository: ICreditHistoryRepository,
    private val paymentCompletedMessagePublisher: IPaymentCompletedMessagePublisher,
    private val paymentCanceledMessagePublisher: IPaymentCanceledMessagePublisher,
    private val paymentFailedMessagePublisher: IPaymentFailedMessagePublisher
) {
    companion object { private val logger = Logger.getLogger(PaymentApplicationServiceRequestHelper::class.java.name) }

    private fun persistObjectsState(
        payment: Payment,
        creditEntry: CreditEntry?,
        creditHistories: List<CreditHistory>,
        errorMessages: List<String>,
    ) {
        paymentRepository.save(payment)
        when {
            errorMessages.isEmpty() -> {
                creditEntryRepository.save(creditEntry!!)
                creditHistoryRepository.save(creditHistories[creditHistories.lastIndex])
            }
        }
    }

    @Transactional
    open fun persistPayment(paymentServiceRequestDTO: PaymentServiceRequestDTO): PaymentEvent {
        logger.info("Received payment completed event for order #${paymentServiceRequestDTO.orderId}")
        val payment = paymentServiceDataMapper.paymentRequestModelToPayment(paymentServiceRequestDTO)

        val creditEntry = getCreditEntry(payment.customerId)
        val creditHistories = getCreditHistory(payment.customerId)?.toMutableList()
        val errorMessages = mutableListOf<String>()

        val paymentEvent = paymentDomainService.validateAndStartPayment(
            payment, creditEntry!!, creditHistories!!, errorMessages,
            paymentCompletedMessagePublisher, paymentFailedMessagePublisher
        )

        persistObjectsState(payment, creditEntry, creditHistories, errorMessages)

        return paymentEvent
    }

    @Transactional
    open fun persistCancelPayment(paymentServiceRequestDTO: PaymentServiceRequestDTO): PaymentEvent {
        logger.info("Received payment rollback event for order #${paymentServiceRequestDTO.orderId}")
        val paymentResponse = paymentRepository.findByOrderId(UUID.fromString(paymentServiceRequestDTO.orderId))
        if (paymentResponse == null) {
            logger.info("Order #${paymentServiceRequestDTO.orderId}'s payment couldn't be found")
            throw PaymentApplicationServiceException(
                "Order #${paymentServiceRequestDTO.orderId}'s payment couldn't be found"
            )
        }

        val creditEntry = getCreditEntry(paymentResponse.customerId)
        val creditHistories = getCreditHistory(paymentResponse.customerId)?.toMutableList()
        val errorMessages = mutableListOf<String>()

        val paymentEvent = paymentDomainService.validateAndCancelPayment(
            paymentResponse, creditEntry!!, creditHistories!!, errorMessages,
            paymentCanceledMessagePublisher, paymentFailedMessagePublisher
        )

        persistObjectsState(paymentResponse, creditEntry, creditHistories, errorMessages)
        return paymentEvent
    }

    private fun getCreditHistory(customerId: CustomerId?): List<CreditHistory>? {
        val creditHistories = creditHistoryRepository.findByCustomerId(customerId!!)
        when {
            creditHistories?.isEmpty() == true -> {
                logger.info("Couldn't find credit history for customer #${customerId.getValue()}")
                throw PaymentApplicationServiceException(
                    "Couldn't find credit history for customer #${customerId.getValue()}"
                )
            }
        }
        return creditHistories
    }

    private fun getCreditEntry(customerId: CustomerId?): CreditEntry? {
        val creditEntry = creditEntryRepository.findCustomerById(customerId!!)
        when (creditEntry) {
            null -> {
                logger.info("Couldn't find credit entry for customer #${customerId.getValue()}")
                throw PaymentApplicationServiceException(
                    "Couldn't find credit entry for customer #${customerId.getValue()}"
                )
            }
        }
        return creditEntry
    }
}
