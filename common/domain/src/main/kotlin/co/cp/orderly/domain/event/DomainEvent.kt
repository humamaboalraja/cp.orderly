package co.cp.orderly.domain.event

open interface DomainEvent<T> {
    fun fire()
}
