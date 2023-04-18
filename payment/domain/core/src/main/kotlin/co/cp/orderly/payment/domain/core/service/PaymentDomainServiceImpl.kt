package co.cp.orderly.payment.domain.core.service

import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.payment.domain.core.IPaymentDomainService
import co.cp.orderly.payment.domain.core.constants.PaymentServiceDomainConstants
import co.cp.orderly.payment.domain.core.entity.CreditEntry
import co.cp.orderly.payment.domain.core.entity.CreditHistory
import co.cp.orderly.payment.domain.core.entity.Payment
import co.cp.orderly.payment.domain.core.events.PaymentCancelledEvent
import co.cp.orderly.payment.domain.core.events.PaymentCompletedEvent
import co.cp.orderly.payment.domain.core.events.PaymentEvent
import co.cp.orderly.payment.domain.core.events.PaymentFailedEvent
import co.cp.orderly.payment.domain.core.vos.CreditHistoryId
import co.cp.orderly.payment.domain.core.vos.TransactionType
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import java.util.logging.Logger

class PaymentDomainServiceImpl : IPaymentDomainService {

    companion object { private val logger = Logger.getLogger(PaymentDomainServiceImpl::class.java.name) }

    override fun validateAndStartPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistory: MutableList<CreditHistory>,
        errorMessages: MutableList<String>,
    ): PaymentEvent {

        payment.validatePayment(errorMessages)
        payment.startPayment()
        validateCreditEntry(payment, creditEntry, errorMessages)
        subtractCreditEntry(payment, creditEntry)
        updateCreditEntry(payment, creditHistory, TransactionType.DEBIT)
        validateCreditHistory(creditEntry, creditHistory, errorMessages)

        return when (errorMessages.isEmpty()) {
            true -> {
                logger.info("Order #${payment.customerId?.getValue()}'s is initiated")
                payment.updateStatus(PaymentStatus.COMPLETED)
                PaymentCompletedEvent(
                    payment, ZonedDateTime.now(ZoneId.of(PaymentServiceDomainConstants.TIMEZONE))
                )
            }
            else -> {
                logger.info("Order #${payment.customerId?.getValue()}'s payment has failed while initiating")
                payment.updateStatus(PaymentStatus.FAILED)
                PaymentFailedEvent(
                    payment,
                    ZonedDateTime.now(ZoneId.of(PaymentServiceDomainConstants.TIMEZONE)),
                    errorMessages
                )
            }
        }
    }

    override fun validateAndCancelPayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistory: MutableList<CreditHistory>,
        errorMessages: MutableList<String>
    ): PaymentEvent {
        payment.validatePayment(errorMessages)
        addCreditEntry(payment, creditEntry)
        updateCreditEntry(payment, creditHistory, TransactionType.CREDIT)

        return when (errorMessages.isEmpty()) {
            true -> {
                logger.info("Order #${payment.orderId?.getValue()}'s Payment has been canceled")
                payment.updateStatus(PaymentStatus.CANCELLED)
                PaymentCancelledEvent(
                    payment, ZonedDateTime.now(ZoneId.of(PaymentServiceDomainConstants.TIMEZONE)),
                )
            }
            else -> {
                logger.info("Order #${payment.orderId?.getValue()}'s Payment failed has failed")
                payment.updateStatus(PaymentStatus.FAILED)
                PaymentFailedEvent(
                    payment, ZonedDateTime.now(ZoneId.of(PaymentServiceDomainConstants.TIMEZONE)), errorMessages
                )
            }
        }
    }

    private fun addCreditEntry(payment: Payment, creditEntry: CreditEntry) =
        run { creditEntry.addAmount(payment.price!!) }

    private fun validateCreditEntry(payment: Payment, creditEntry: CreditEntry, errorMessages: MutableList<String>) {
        when {
            payment.price!!.isAmountGreaterThan(creditEntry.totalCreditAmount) -> {
                logger.info(
                    "Customer #${payment.customerId?.getValue()} doesn't have enough funds for this payment"
                )
                errorMessages.add(
                    "Customer #${payment.customerId?.getValue()} doesn't have enough funds for this payment"
                )
            }
        }
    }

    private fun subtractCreditEntry(payment: Payment, creditEntry: CreditEntry) =
        creditEntry.subtractAmount(payment.price!!)

    private fun updateCreditEntry(
        payment: Payment,
        creditHistory: MutableList<CreditHistory>,
        transactionType: TransactionType
    ) = creditHistory.add(
        CreditHistory(CreditHistoryId(UUID.randomUUID()), payment.customerId!!, payment.price!!, transactionType)
    )

    private fun validateCreditHistory(
        creditEntry: CreditEntry,
        creditHistory: MutableList<CreditHistory>,
        errorMessages: MutableList<String>
    ) {
        val totalCreditHistory = getTotalHistoryTransactionTypeAmount(creditHistory, TransactionType.CREDIT)
        val totalDebitHistory = getTotalHistoryTransactionTypeAmount(creditHistory, TransactionType.DEBIT)

        when {
            totalDebitHistory.isAmountGreaterThan(totalCreditHistory) -> {
                logger.info("Customer #${creditEntry.customerId.getValue()} doesn't have enough credit")
                errorMessages.add("Customer #${creditEntry.customerId.getValue()} doesn't have enough credit")
            }

            creditEntry.totalCreditAmount != totalCreditHistory.subtractMoney(totalDebitHistory) -> {
                logger.info(
                    "Customer #${creditEntry.customerId.getValue()}'s credit history doesn't equal current credit"
                )
                errorMessages.add(
                    "Customer #${creditEntry.customerId.getValue()}'s credit history doesn't equal current credit"
                )
            }
        }
    }

    private fun getTotalHistoryTransactionTypeAmount(
        creditHistory: MutableList<CreditHistory>,
        transactionType: TransactionType
    ) = creditHistory.filter { transactionType == it.transactionType }
        .map { it.amount }.reduce { acc, money -> money.addMoney(acc) }
}
