package com.lukinhasssss.assinatura.domain.subscription

interface SubscriptionGateway {
    fun subscriptionOfId(id: SubscriptionId): Subscription?

    fun save(subscription: Subscription): Subscription

    fun latestSubscriptionOfAccount(accountId: String): Subscription?

    fun nextId(): SubscriptionId
}
