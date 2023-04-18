package co.cp.orderly.payment.data.consistency.adapter

import ConsistencyState
import co.cp.orderly.domain.vos.PaymentStatus
import co.cp.orderly.payment.data.consistency.exception.OrderConsistencyNotFoundException
import co.cp.orderly.payment.data.consistency.mapper.OrderConsistencyDataMapper
import co.cp.orderly.payment.data.consistency.repository.OrderConsistencyPersistenceRepository
import co.cp.orderly.payment.domain.service.consistency.model.OrderConsistencyMessage
import co.cp.orderly.payment.domain.service.ports.outptut.repository.IOrderConsistencyRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderConsistencyRepositoryImpl(
    private val orderConsistencyPersistenceRepository: OrderConsistencyPersistenceRepository,
    private val orderConsistencyDataMapper: OrderConsistencyDataMapper
) : IOrderConsistencyRepository {

    override fun save(orderPaymentConsistencyMessage: OrderConsistencyMessage) =
        orderConsistencyDataMapper
            .orderConsistencyEntityToOrderConsistencyMessage(
                orderConsistencyPersistenceRepository.save(
                    orderConsistencyDataMapper.orderConsistencyMessageToConsistencyEntity(
                        orderPaymentConsistencyMessage
                    )!!
                )
            )

    override fun findByTypeAndConsistencyState(
        lltType: String,
        consistencyStatus: ConsistencyState
    ) = orderConsistencyPersistenceRepository.findByTypeAndConsistencyState(lltType, consistencyStatus).also {
        if (it == null)
            throw OrderConsistencyNotFoundException("Approval consistency object cannot be found for llt type $lltType")
    }?.map(orderConsistencyDataMapper::orderConsistencyEntityToOrderConsistencyMessage)?.toList()

    override fun findByTypeAndLltIdAndPaymentStatusAndConsistencyState(
        lltType: String?,
        lltId: UUID?,
        paymentStatus: PaymentStatus?,
        consistencyStatus: ConsistencyState?
    ) = orderConsistencyDataMapper.orderConsistencyEntityToOrderConsistencyMessage(
        orderConsistencyPersistenceRepository.findByTypeAndLltIdAndPaymentStatusAndConsistencyState(
            lltType, lltId,
            paymentStatus, consistencyStatus
        )!!
    )

    override fun deleteByTypeAndConsistencyState(lltType: String, consistencyStatus: ConsistencyState) =
        orderConsistencyPersistenceRepository.deleteByTypeAndConsistencyState(lltType, consistencyStatus)
}
