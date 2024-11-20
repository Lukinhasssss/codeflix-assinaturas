package com.lukinhasssss.assinatura.domain

import java.time.Instant

interface DomainEvent : AssertionConcern {
    val occurredOn: Instant
    val aggregateId: String
    val aggregateType: String
}
