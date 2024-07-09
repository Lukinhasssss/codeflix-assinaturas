package com.lukinhasssss.admin.catalogo.domain

import com.lukinhasssss.admin.catalogo.domain.event.DomainEvent
import com.lukinhasssss.assinatura.domain.AssertionConcern

abstract class Entity<ID : Identifier>(
    val id: ID,
    val domainEvents: MutableList<DomainEvent> = mutableListOf(),
) : AssertionConcern {
    fun domainEvents(): List<DomainEvent> = domainEvents.toList()

    fun registerEvent(event: DomainEvent) {
        domainEvents.add(event)
    }
}
