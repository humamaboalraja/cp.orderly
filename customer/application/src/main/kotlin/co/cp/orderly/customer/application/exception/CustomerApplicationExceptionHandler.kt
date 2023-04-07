package co.cp.orderly.customer.application.exception

import co.cp.orderly.common.application.handler.ErrorDTO
import co.cp.orderly.common.application.handler.GlobalExceptionHandler
import co.cp.orderly.customer.domain.core.exception.CustomerDomainException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.logging.Logger

@ControllerAdvice
class CustomerApplicationExceptionHandler: GlobalExceptionHandler() {

    companion object { private val logger = Logger.getLogger(CustomerApplicationExceptionHandler::class.java.name) }

    @ResponseBody
    @ExceptionHandler(value = [CustomerDomainException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(exception: CustomerDomainException): ErrorDTO {
        logger.info("${exception.message}, $exception")
        return ErrorDTO(HttpStatus.BAD_REQUEST.reasonPhrase, exception.message!!)
    }
}
