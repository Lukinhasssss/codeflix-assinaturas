package com.lukinhasssss.assinatura.application.subscription

import com.lukinhasssss.assinatura.application.UseCase
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId

abstract class CreateSubscription : UseCase<CreateSubscription.Input, CreateSubscription.Output>() {
    interface Input {
        val accountId: String
        val planId: String
    }

    interface Output {
        val subscriptionId: SubscriptionId
    }
}
