package co.cp.orderly.shop.domain.core.exception

import co.cp.orderly.domain.exception.DomainException

class ShopDomainException : DomainException {
    constructor(message: String) : super(message)
    constructor(message: String?, cause: Throwable) : super(message, cause)
}
