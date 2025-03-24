package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus

sealed interface SubscriptionCommand {
    data class ChangeStatus(
        val status: SubscriptionStatus,
    ) : SubscriptionCommand
}
