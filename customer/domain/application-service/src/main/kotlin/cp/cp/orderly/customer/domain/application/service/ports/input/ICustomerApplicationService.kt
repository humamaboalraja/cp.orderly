package cp.cp.orderly.customer.domain.application.service.ports.input

import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerCommandDTO
import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerResponseDTO
import jakarta.validation.Valid

interface ICustomerApplicationService {
    fun createCustomer(@Valid createCustomerCommand: CreateCustomerCommandDTO): CreateCustomerResponseDTO
}
