package cp.cp.orderly.customer.domain.application.service.dto.customer

import java.util.UUID
import javax.validation.constraints.NotNull

data class CreateCustomerCommandDTO(
    @NotNull
    val customerId: UUID? = null,

    @NotNull
    val username: String? = null,

    @NotNull
    val firstName: String? = null,

    @NotNull
    val lastName: String? = null
)
