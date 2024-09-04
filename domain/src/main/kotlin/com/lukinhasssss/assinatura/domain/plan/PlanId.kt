package com.lukinhasssss.assinatura.domain.plan

import com.lukinhasssss.admin.catalogo.domain.Identifier

data class PlanId(override val value: String) : Identifier {
    init {
        assertArgumentNotEmpty(value, "'planId' should not be empty")
    }
}
