package co.cp.orderly.order.data.customer.repository

import co.cp.orderly.order.data.customer.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerPersistenceRepository : JpaRepository<CustomerEntity, UUID>
