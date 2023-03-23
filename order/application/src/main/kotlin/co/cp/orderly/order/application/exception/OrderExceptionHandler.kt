package co.cp.orderly.order.application.exception

import co.cp.orderly.common.application.handler.ErrorDTO
import co.cp.orderly.common.application.handler.GlobalExceptionHandler
import co.cp.orderly.order.domain.core.exception.OrderDomainException
import co.cp.orderly.order.domain.core.exception.OrderNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.logging.Logger

@ControllerAdvice
class OrderExceptionHandler : GlobalExceptionHandler() {

    companion object { private val logger = Logger.getLogger(OrderExceptionHandler::class.java.name) }

    @ResponseBody
    @ExceptionHandler(value = [OrderDomainException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(orderDomainException: OrderDomainException): ErrorDTO {
        logger.severe("${orderDomainException.message}, $orderDomainException")
        return ErrorDTO(HttpStatus.BAD_REQUEST.reasonPhrase, orderDomainException.message!!)
    }

    @ResponseBody
    @ExceptionHandler(value = [OrderNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleException(orderNotFoundException: OrderNotFoundException): ErrorDTO {
        logger.severe("${orderNotFoundException.message}, $orderNotFoundException")
        return ErrorDTO(HttpStatus.NOT_FOUND.reasonPhrase, orderNotFoundException.message!!)
    }
}
