package cp.cp.orderly.customer.domain.application.service.dto.customer

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateCustomerResponseDTO(
    @NotNull
    private val customerId: UUID? = null,
    @NotNull
    private val message: String? = null
)
