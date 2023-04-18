package co.cp.orderly.shop.data.consistency.adapter

import ConsistencyState
import co.cp.orderly.shop.application.service.consistency.model.OrderConsistencyMessage
import co.cp.orderly.shop.application.service.ports.output.repository.IOrderConsistencyRepository
import co.cp.orderly.shop.data.consistency.exception.OrderConsistencyNotFoundException
import co.cp.orderly.shop.data.consistency.mapper.OrderConsistencyDataLayerDataMapper
import co.cp.orderly.shop.data.consistency.repository.OrderConsistencyPersistenceRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderConsistencyRepositoryImpl(
    private val orderConsistencyPersistenceRepository: OrderConsistencyPersistenceRepository,
    private val orderConsistencyDataLayerDataMapper: OrderConsistencyDataLayerDataMapper,

) : IOrderConsistencyRepository {

    override fun save(orderPaymentConsistencyMessage: OrderConsistencyMessage?) =
        orderConsistencyDataLayerDataMapper
            .orderConsistencyEntityToOrderConsistencyMessage(
                orderConsistencyPersistenceRepository
                    .save(
                        orderConsistencyDataLayerDataMapper
                            .orderConsistencyMessageToConsistencyEntity(orderPaymentConsistencyMessage!!)
                    )
            )

    override fun findByTypeAndConsistencyState(
        lltType: String?,
        consistencyStatus: ConsistencyState?
    ) = orderConsistencyPersistenceRepository.findByTypeAndConsistencyState(lltType, consistencyStatus)
        ?.map(orderConsistencyDataLayerDataMapper::orderConsistencyEntityToOrderConsistencyMessage)?.toList()
        ?: throw OrderConsistencyNotFoundException("Approval consistency object cannot be found for llt $lltType")

    override fun findByTypeAndLltIdAndConsistencyState(
        type: String?,
        lltId: UUID?,
        consistencyState: ConsistencyState?
    ) = orderConsistencyDataLayerDataMapper.orderConsistencyEntityToOrderConsistencyMessage(
        orderConsistencyPersistenceRepository.findByTypeAndLltIdAndConsistencyState(type, lltId, consistencyState)!!
    )

    override fun deleteByTypeAndConsistencyState(type: String?, consistencyStatus: ConsistencyState?) {
        orderConsistencyPersistenceRepository.deleteByTypeAndConsistencyState(type, consistencyStatus)
    }
}
