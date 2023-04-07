package cp.cp.orderly.customer.domain.application.service.dto.customer

import java.util.UUID
import javax.validation.constraints.NotNull

data class CreateCustomerResponseDTO(
    @NotNull
    private val customerId: UUID? = null,
    @NotNull
    private val message: String? = null
)
