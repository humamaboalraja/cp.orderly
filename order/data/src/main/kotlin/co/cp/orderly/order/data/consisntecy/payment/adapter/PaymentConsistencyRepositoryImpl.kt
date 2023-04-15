package co.cp.orderly.order.data.consisntecy.payment.adapter

import ConsistencyState
import co.cp.orderly.infrastructure.transactions.llt.LongRunningTransactionState
import co.cp.orderly.order.data.consisntecy.payment.exception.PaymentConsistencyNotFoundException
import co.cp.orderly.order.data.consisntecy.payment.mapper.PaymentConsistencyDataLayerDataMapper
import co.cp.orderly.order.data.consisntecy.payment.repository.PaymentConsistencyPersistenceRepository
import co.cp.orderly.order.domain.application.service.consistency.model.payment.OrderPaymentConsistencyMessage
import co.cp.orderly.order.domain.application.service.ports.output.repository.PaymentConsistencyRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PaymentConsistencyRepositoryImpl(
    private val paymentConsistencyPersistenceRepository: PaymentConsistencyPersistenceRepository,
    private val paymentConsistencyDataLayerDataMapper: PaymentConsistencyDataLayerDataMapper
) : PaymentConsistencyRepository {

    override fun save(orderPaymentConsistencyMessage: OrderPaymentConsistencyMessage?): OrderPaymentConsistencyMessage =
        paymentConsistencyDataLayerDataMapper
            .paymentConsistencyEntityToOrderPaymentConsistencyMessage(
                paymentConsistencyPersistenceRepository
                    .save(
                        paymentConsistencyDataLayerDataMapper
                            .orderPaymentConsistencyMessageToConsistencyEntity(orderPaymentConsistencyMessage!!)
                    )
            )

    override fun findByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState
    ): List<OrderPaymentConsistencyMessage> =
        paymentConsistencyPersistenceRepository.findByTypeAndConsistencyStateAndLltStateIn(
            type,
            consistencyState,
            listOf(*lltState)
        )!!.map(paymentConsistencyDataLayerDataMapper::paymentConsistencyEntityToOrderPaymentConsistencyMessage)
            .toList().also {
                if (it == null) {
                    throw PaymentConsistencyNotFoundException(
                        "Payment Consistency object couldn't be found for Llt type #$type"
                    )
                }
            }

    override fun findByTypeAndLltIdAndLltState(
        type: String?,
        lltId: UUID?,
        vararg lltState: LongRunningTransactionState
    ): OrderPaymentConsistencyMessage =
        paymentConsistencyDataLayerDataMapper.paymentConsistencyEntityToOrderPaymentConsistencyMessage(
            paymentConsistencyPersistenceRepository
                .findByTypeAndLltIdAndLltStateIn(type, lltId, listOf(*lltState))!!
        )

    override fun deleteByTypeAndConsistencyStateAndLltState(
        type: String?,
        consistencyState: ConsistencyState?,
        vararg lltState: LongRunningTransactionState
    ) = paymentConsistencyPersistenceRepository.deleteByTypeAndConsistencyStateAndLongLltIn(
        type, consistencyState, listOf(*lltState)
    )
}
