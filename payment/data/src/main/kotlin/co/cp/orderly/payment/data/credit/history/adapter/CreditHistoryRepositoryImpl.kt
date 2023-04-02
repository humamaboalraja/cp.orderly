package co.cp.orderly.payment.data.credit.history.adapter

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.payment.data.credit.history.mapper.CreditHistoryDataMapper
import co.cp.orderly.payment.data.credit.history.repository.CreditHistoryPersistenceRepository
import co.cp.orderly.payment.domain.core.entity.CreditHistory
import co.cp.orderly.payment.domain.service.ports.outptut.repository.ICreditHistoryRepository
import org.springframework.stereotype.Component

@Component
class CreditHistoryRepositoryImpl(
    private val creditHistoryPersistenceRepository: CreditHistoryPersistenceRepository,
    private val creditHistoryDataMapper: CreditHistoryDataMapper
) : ICreditHistoryRepository {

    override fun save(creditHistory: CreditHistory): CreditHistory =
        creditHistoryDataMapper
            .mapCreditHistoryEntityToCreditHistory(
                creditHistoryPersistenceRepository
                    .save(creditHistoryDataMapper.mapCreditHistoryToCreditHistoryEntity(creditHistory))
            )

    override fun findByCustomerId(customerId: CustomerId): List<CreditHistory>? =
        creditHistoryPersistenceRepository.findByCustomerId(customerId.getValue())?.map {
            creditHistoryDataMapper.mapCreditHistoryEntityToCreditHistory(it)
        }?.toList()
}
