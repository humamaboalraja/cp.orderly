package co.cp.orderly.domain.exception

open class DomainException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String?, cause: Throwable) : super(message, cause)
}
