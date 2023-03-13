package co.cp.orderly.domain.event.publisher

import co.cp.orderly.domain.event.DomainEvent

interface DomainEventPublisher<T : DomainEvent<*>> {
    fun publish(domainEvent: T)
}
