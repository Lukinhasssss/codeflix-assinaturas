package com.lukinhasssss.admin.catalogo.domain.event

import com.lukinhasssss.assinatura.domain.AssertionConcern
import java.io.Serializable
import java.time.Instant

interface DomainEvent : AssertionConcern {
    val occurredOn: Instant
    val aggregateId: String
    val aggregateType: String
}
