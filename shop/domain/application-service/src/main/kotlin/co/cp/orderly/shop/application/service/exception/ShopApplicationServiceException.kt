package co.cp.orderly.shop.application.service.exception

import co.cp.orderly.domain.exception.DomainException

class ShopApplicationServiceException : DomainException {
    constructor(message: String) : super(message)
    constructor(message: String?, cause: Throwable) : super(message, cause)
}
