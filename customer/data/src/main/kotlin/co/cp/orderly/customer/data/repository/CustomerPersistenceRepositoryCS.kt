package co.cp.orderly.customer.data.repository

import co.cp.orderly.customer.data.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerPersistenceRepositoryCS : JpaRepository<CustomerEntity, UUID>
