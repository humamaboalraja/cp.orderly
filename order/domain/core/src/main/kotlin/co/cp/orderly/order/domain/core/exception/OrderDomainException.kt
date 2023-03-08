package co.cp.orderly.order.domain.core.exception

import co.cp.orderly.domain.exception.DomainException

class OrderDomainException : DomainException {
    constructor(message: String) : super(message)
    constructor(message: String?, cause: Throwable) : super(message, cause)
}
