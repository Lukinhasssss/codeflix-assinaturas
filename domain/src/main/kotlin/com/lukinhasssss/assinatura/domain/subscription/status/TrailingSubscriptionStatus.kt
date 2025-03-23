package com.lukinhasssss.assinatura.domain.subscription.status

import com.lukinhasssss.assinatura.domain.subscription.Subscription

data class TrailingSubscriptionStatus(
    val subscription: Subscription,
) : SubscriptionStatus {
    override fun trailing() {}

    override fun incomplete() {}

    override fun active() {}

    override fun cancel() {}
}
