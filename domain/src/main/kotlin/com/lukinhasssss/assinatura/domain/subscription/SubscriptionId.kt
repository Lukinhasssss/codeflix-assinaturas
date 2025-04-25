package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.Identifier

data class SubscriptionId(override val value: String) : Identifier {
    init {
        assertArgumentNotEmpty(value, "'subscriptionId' should not be empty")
    }
}
