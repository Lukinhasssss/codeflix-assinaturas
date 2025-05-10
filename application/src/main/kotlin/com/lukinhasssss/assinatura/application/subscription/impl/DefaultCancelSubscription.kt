package com.lukinhasssss.assinatura.application.subscription.impl

import com.lukinhasssss.assinatura.application.subscription.CancelSubscription
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionCommand
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId

class DefaultCancelSubscription(
    private val subscriptionGateway: SubscriptionGateway,
) : CancelSubscription() {
    override fun execute(input: Input): Output {
        val subscriptionId = SubscriptionId(input.subscriptionId)

        val aSubscription =
            subscriptionGateway.subscriptionOfId(subscriptionId)
                ?.takeIf { it.accountId == AccountId(input.accountId) }
                ?: throw DomainException.notFound(Subscription::class, subscriptionId)

        aSubscription.execute(SubscriptionCommand.CancelSubscription())
        subscriptionGateway.save(aSubscription)

        return object : Output {
            override val subscriptionId = subscriptionId
            override val subscriptionStatus = aSubscription.status.value()
        }
    }
}
