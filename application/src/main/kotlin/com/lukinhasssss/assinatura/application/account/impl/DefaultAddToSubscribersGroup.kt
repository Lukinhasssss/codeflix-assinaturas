package com.lukinhasssss.assinatura.application.account.impl

import com.lukinhasssss.assinatura.application.account.AddToSubscribersGroup
import com.lukinhasssss.assinatura.domain.account.Account
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.account.idp.GroupId
import com.lukinhasssss.assinatura.domain.account.idp.IdentityProviderGateway
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId

class DefaultAddToSubscribersGroup(
    private val identityProviderGateway: IdentityProviderGateway,
    private val subscriptionGateway: SubscriptionGateway,
    private val accountGateway: AccountGateway,
) : AddToSubscribersGroup() {
    override fun execute(input: Input): Output {
        val anAccountId = AccountId(input.accountId)
        val aSubscriptionId = SubscriptionId(input.subscriptionId)

        val aSubscription =
            subscriptionGateway.subscriptionOfId(aSubscriptionId)
                ?.takeIf { it.accountId == anAccountId }
                ?: throw DomainException.notFound(Subscription::class, aSubscriptionId)

        if (aSubscription.isTrial() || aSubscription.isActive()) {
            val userId =
                accountGateway.accountOfId(anAccountId)
                    ?.userId
                    ?: throw DomainException.notFound(Account::class, anAccountId)

            identityProviderGateway.addUserToGroup(userId, GroupId.from(input.groupId))
        }

        return object : Output {
            override val subscriptionId = aSubscriptionId
        }
    }
}
