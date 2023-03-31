package co.cp.orderly.payment.domain.core.exception

import co.cp.orderly.domain.exception.DomainException

class PaymentDomainException : DomainException {
    constructor(message: String) : super(message)
    constructor(message: String?, cause: Throwable) : super(message, cause)
}
