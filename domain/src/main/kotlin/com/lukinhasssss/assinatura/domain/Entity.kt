package com.lukinhasssss.assinatura.domain

abstract class Entity<ID : Identifier>(
    val id: ID,
    val domainEvents: MutableList<DomainEvent> = mutableListOf(),
) : AssertionConcern {
    fun domainEvents(): List<DomainEvent> = domainEvents.toList()

    fun registerEvent(event: DomainEvent) {
        domainEvents.add(event)
    }
}
