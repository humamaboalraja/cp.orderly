package cp.cp.orderly.customer.domain.application.service.mapper

import co.cp.orderly.customer.domain.core.entity.Customer
import co.cp.orderly.domain.vos.CustomerId
import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerCommandDTO
import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerResponseDTO
import org.springframework.stereotype.Component

@Component
class CustomerApplicationServiceDataMapper {
    fun convertCustomerCommandToCustomer(createCustomerCommand: CreateCustomerCommandDTO): Customer = Customer(
        CustomerId(createCustomerCommand.customerId!!),
        createCustomerCommand.username!!,
        createCustomerCommand.email!!,
        createCustomerCommand.firstName!!,
        createCustomerCommand.lastName!!
    )

    fun convertCustomerToCreateCustomerResponse(customer: Customer, message: String?): CreateCustomerResponseDTO =
        CreateCustomerResponseDTO(customer.getId()?.getValue(), message)
}
