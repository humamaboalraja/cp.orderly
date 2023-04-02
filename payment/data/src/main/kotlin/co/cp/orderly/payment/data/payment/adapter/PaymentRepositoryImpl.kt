package co.cp.orderly.payment.data.payment.adapter

import co.cp.orderly.payment.data.payment.mapper.PaymentDataMapper
import co.cp.orderly.payment.data.payment.repository.PaymentPersistenceRepository
import co.cp.orderly.payment.domain.core.entity.Payment
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PaymentRepositoryImpl(
    private val paymentPersistenceRepository: PaymentPersistenceRepository,
    private val paymentDataMapper: PaymentDataMapper
) {

    fun save(payment: Payment): Payment =
        paymentDataMapper
            .mapPaymentEntityToPayment(
                paymentPersistenceRepository.save(paymentDataMapper.mapPaymentToPaymentEntity(payment))
            )

    fun findByOrderId(orderId: UUID): Payment =
        paymentDataMapper.mapPaymentEntityToPayment(paymentPersistenceRepository.findByOrderId(orderId)!!)
}
