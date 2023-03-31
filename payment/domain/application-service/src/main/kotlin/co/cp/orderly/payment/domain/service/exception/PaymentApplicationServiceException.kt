package co.cp.orderly.payment.domain.service.exception

import co.cp.orderly.domain.exception.DomainException

class PaymentApplicationServiceException : DomainException {
    constructor(message: String) : super(message)
    constructor(message: String?, cause: Throwable) : super(message, cause)
}
