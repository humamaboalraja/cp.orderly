package co.cp.orderly.payment.domain.service.service.helper

import ConsistencyState
import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.payment.domain.core.IPaymentDomainService
import co.cp.orderly.payment.domain.core.entity.CreditEntry
import co.cp.orderly.payment.domain.core.entity.CreditHistory
import co.cp.orderly.payment.domain.core.entity.Payment
import co.cp.orderly.payment.domain.service.consistency.scheduler.OrderConsistencyUtil
import co.cp.orderly.payment.domain.service.dto.PaymentServiceRequestDTO
import co.cp.orderly.payment.domain.service.exception.PaymentApplicationServiceException
import co.cp.orderly.payment.domain.service.mapper.PaymentServiceDataMapper
import co.cp.orderly.payment.domain.service.ports.outptut.message.publisher.IPaymentResponseMessagePublisher
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
    private val orderConsistencyUtil: OrderConsistencyUtil,
    private val paymentResponseMessagePublisher: IPaymentResponseMessagePublisher
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
    open fun persistPayment(paymentServiceRequestDTO: PaymentServiceRequestDTO) {
        if (publishIfConsistencyMessageProcessedForPayment(paymentServiceRequestDTO, PaymentStatus.COMPLETED)) {
            logger.info(
                "Consistency message of llt #${paymentServiceRequestDTO.lltId} has already been persistes"
            )
            return
        }
        logger.info("Received payment completed event for order #${paymentServiceRequestDTO.orderId}")
        val payment = paymentServiceDataMapper.paymentRequestModelToPayment(paymentServiceRequestDTO)

        val creditEntry = getCreditEntry(payment.customerId)
        val creditHistories = getCreditHistory(payment.customerId)?.toMutableList()
        val errorMessages = mutableListOf<String>()

        val paymentEvent = paymentDomainService.validateAndStartPayment(
            payment, creditEntry!!, creditHistories!!, errorMessages
        )

        persistObjectsState(payment, creditEntry, creditHistories, errorMessages)

        orderConsistencyUtil.saveOrderConsistencyMessage(
            paymentServiceDataMapper.paymentEventToOrderEventPayload(paymentEvent),
            paymentEvent.payment.paymentStatus,
            ConsistencyState.STARTED,
            UUID.fromString(paymentServiceRequestDTO.lltId)
        )
    }

    @Transactional
    open fun persistCancelPayment(paymentServiceRequestDTO: PaymentServiceRequestDTO) {
        if (publishIfConsistencyMessageProcessedForPayment(paymentServiceRequestDTO, PaymentStatus.CANCELLED)) {
            logger.info(
                "Consistency message of llt #${paymentServiceRequestDTO.lltId} has already been persisted"
            )
            return
        }
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
            paymentResponse, creditEntry!!, creditHistories!!, errorMessages
        )

        persistObjectsState(paymentResponse, creditEntry, creditHistories, errorMessages)
        orderConsistencyUtil.saveOrderConsistencyMessage(
            paymentServiceDataMapper.paymentEventToOrderEventPayload(paymentEvent),
            paymentEvent.payment.paymentStatus,
            ConsistencyState.STARTED,
            UUID.fromString(paymentServiceRequestDTO.lltId)
        )
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

    open fun publishIfConsistencyMessageProcessedForPayment(
        paymentRequest: PaymentServiceRequestDTO,
        paymentStatus: PaymentStatus
    ): Boolean {
        val orderConsistencyMessage =
            orderConsistencyUtil.getCompletedOrderConsistencyMessageByLltIdAndPaymentStatus(
                UUID.fromString(paymentRequest.lltId), paymentStatus
            )
        if (orderConsistencyMessage != null) {
            paymentResponseMessagePublisher.publish(orderConsistencyMessage!!, orderConsistencyUtil::updateConsistencyMessage)
            return true
        }
        return false
    }

    private fun getCreditEntry(customerId: CustomerId?): CreditEntry? {
        val creditEntry = creditEntryRepository.findByCustomerId(customerId!!)
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
