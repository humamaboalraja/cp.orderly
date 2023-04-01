package co.cp.orderly.payment.data.credit.entry.adapter

import co.cp.orderly.domain.vos.CustomerId
import co.cp.orderly.payment.data.credit.entry.mapper.CreditEntryDataMapper
import co.cp.orderly.payment.data.credit.entry.repository.CreditEntryPersistenceRepository
import co.cp.orderly.payment.domain.core.entity.CreditEntry
import co.cp.orderly.payment.domain.service.ports.outptut.repository.ICreditEntryRepository
import org.springframework.stereotype.Component

@Component
class CreditEntryRepositoryImpl(
    private val creditEntryPersistenceRepository: CreditEntryPersistenceRepository,
    private val creditEntryDataMapper: CreditEntryDataMapper
) : ICreditEntryRepository {

    override fun save(creditEntry: CreditEntry): CreditEntry =
        creditEntryDataMapper
            .mapCreditEntryEntityToCreditEntry(
                creditEntryPersistenceRepository
                    .save(creditEntryDataMapper.mapCreditEntryToCreditEntryEntity(creditEntry))
            )

    override fun findByCustomerId(customerId: CustomerId): CreditEntry =
        creditEntryDataMapper.mapCreditEntryEntityToCreditEntry(
            creditEntryPersistenceRepository.findByCustomerId(customerId.getValue())!!
        )
}
