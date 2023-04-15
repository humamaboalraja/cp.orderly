package co.cp.orderly.common.application.handler

import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.logging.Logger

@ControllerAdvice
open class GlobalExceptionHandler {

    companion object {
        private val logger = Logger.getLogger(GlobalExceptionHandler::class.java.name)
    }

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception): ErrorDTO {
        logger.info("${exception.message}, $exception")
        return ErrorDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            "Unexpected error just occurred!"
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [ValidationException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(validationException: ValidationException): ErrorDTO =
        when (validationException) {
            is ConstraintViolationException -> {
                val violations = extractViolationsFromException(validationException)
                logger.info("$violations, $validationException")
                ErrorDTO(HttpStatus.BAD_REQUEST.reasonPhrase, violations)
            }
            else -> {
                val exceptionMessage = validationException.message
                logger.info("$exceptionMessage, $validationException")
                ErrorDTO(HttpStatus.BAD_REQUEST.reasonPhrase, exceptionMessage!!)
            }
        }

    private fun extractViolationsFromException(validationException: ConstraintViolationException) =
        validationException.constraintViolations.joinToString { "--" }
}
