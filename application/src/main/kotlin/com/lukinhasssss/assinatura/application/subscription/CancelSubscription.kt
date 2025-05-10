package com.lukinhasssss.assinatura.application.subscription

import com.lukinhasssss.assinatura.application.UseCase
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId

abstract class CancelSubscription : UseCase<CancelSubscription.Input, CancelSubscription.Output>() {
    interface Input {
        val accountId: String
        val subscriptionId: String
    }

    interface Output {
        val subscriptionStatus: String
        val subscriptionId: SubscriptionId
    }
}
