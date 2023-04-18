package cp.cp.orderly.customer.domain.application.service.service.customer

import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerCommandDTO
import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerResponseDTO
import cp.cp.orderly.customer.domain.application.service.mapper.CustomerApplicationServiceDataMapper
import cp.cp.orderly.customer.domain.application.service.ports.input.ICustomerApplicationService
import cp.cp.orderly.customer.domain.application.service.ports.output.messaging.ICustomerMessagePublisher
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.logging.Logger

@Validated
@Service
class CustomerApplicationServiceImpl(
    private val customerApplicationServiceDataMapper: CustomerApplicationServiceDataMapper,
    private val customerMessagePublisher: ICustomerMessagePublisher,
    private val createCustomerCommandHandler: CreateCustomerCommandHandler

) : ICustomerApplicationService {

    companion object { private val logger = Logger.getLogger(CustomerApplicationServiceImpl::class.java.name) }

    override fun createCustomer(createCustomerCommand: CreateCustomerCommandDTO): CreateCustomerResponseDTO {
        val customerCreatedEvent = createCustomerCommandHandler.createCustomer(createCustomerCommand)
        customerMessagePublisher.publish(customerCreatedEvent)
        return customerApplicationServiceDataMapper.convertCustomerToCreateCustomerResponse(
            customerCreatedEvent.customer, "Customer has been successfully saved."
        )
    }
}
