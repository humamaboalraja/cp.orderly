package cp.cp.orderly.customer.domain.application.service.dto.customer

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateCustomerCommandDTO(
    @NotNull
    val customerId: UUID? = null,

    @NotNull
    val username: String? = null,

    @NotNull
    val email: String? = null,

    @NotNull
    val firstName: String? = null,

    @NotNull
    val lastName: String? = null
)
