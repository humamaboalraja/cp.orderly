package co.cp.orderly.order.domain.core.exception

import co.cp.orderly.domain.exception.DomainException

class OrderNotFoundException : DomainException {
    constructor(message: String) : super(message)
    constructor(message: String?, cause: Throwable) : super(message, cause)
}
