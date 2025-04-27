package com.lukinhasssss.assinatura.application.account

import com.lukinhasssss.assinatura.application.UseCase
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId

abstract class AddToSubscribersGroup : UseCase<AddToSubscribersGroup.Input, AddToSubscribersGroup.Output>() {
    interface Input {
        val accountId: String
        val groupId: String
        val subscriptionId: String
    }

    interface Output {
        val subscriptionId: SubscriptionId
    }
}
