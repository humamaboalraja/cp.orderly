package co.cp.orderly.customer.application.controller

import co.cp.orderly.customer.application.exception.CustomerApplicationExceptionHandler
import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerCommandDTO
import cp.cp.orderly.customer.domain.application.service.dto.customer.CreateCustomerResponseDTO
import cp.cp.orderly.customer.domain.application.service.ports.input.ICustomerApplicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@RestController
@RequestMapping(value = ["/customers"], produces = ["application/vnd.api.v1+json"])
class CustomerController(val customerApplicationService: ICustomerApplicationService) {

    companion object { private val logger = Logger.getLogger(CustomerApplicationExceptionHandler::class.java.name) }

    @PostMapping
    fun createCustomer(
        @RequestBody createCustomerCommand: CreateCustomerCommandDTO
    ): ResponseEntity<CreateCustomerResponseDTO> {
        logger.info("Creating customer ${createCustomerCommand.username}")
        return ResponseEntity.ok(customerApplicationService.createCustomer(createCustomerCommand))
    }

    @GetMapping
    fun returnStuff(): ResponseEntity<String> {
        return ResponseEntity.ok("dfgdssadsafaghsd")
    }
}
