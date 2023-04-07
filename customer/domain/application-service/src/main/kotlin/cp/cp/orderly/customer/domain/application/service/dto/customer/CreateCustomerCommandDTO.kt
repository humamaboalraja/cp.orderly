package cp.cp.orderly.customer.domain.application.service.dto.customer

import javax.validation.constraints.NotNull
import java.util.UUID

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
