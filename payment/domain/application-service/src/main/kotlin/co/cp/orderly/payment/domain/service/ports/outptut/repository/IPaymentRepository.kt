package co.cp.orderly.payment.domain.service.ports.outptut.repository

import co.cp.orderly.payment.domain.core.entity.Payment
import java.util.UUID

interface IPaymentRepository {
    fun save(payment: Payment): Payment
    fun findByOrderId(orderId: UUID): Payment?
}
