package com.lukinhasssss.assinatura.domain

abstract class AggregateRoot<ID : Identifier>(
    id: ID,
    domainEvents: MutableList<DomainEvent> = mutableListOf(),
) : Entity<ID>(id, domainEvents)
