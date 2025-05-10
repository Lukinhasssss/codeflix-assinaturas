package com.lukinhasssss.assinatura.application.subscription.impl

import com.lukinhasssss.assinatura.application.subscription.CreateSubscription
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.plan.Plan
import com.lukinhasssss.assinatura.domain.plan.PlanGateway
import com.lukinhasssss.assinatura.domain.plan.PlanId
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway

class DefaultCreateSubscription(
    private val subscriptionGateway: SubscriptionGateway,
    private val planGateway: PlanGateway,
    private val accountGateway: AccountGateway,
) : CreateSubscription() {
    override fun execute(input: Input): Output =
        with(input) {
            validateActiveSubscription(accountId)

            val aPlan =
                planGateway.planOfId(PlanId(planId))
                    ?.takeIf { it.isActive }
                    ?: throw DomainException.with("Plan $planId not found")

            val anUserAccount =
                accountGateway.accountOfId(AccountId(accountId))
                    ?: throw DomainException.with("Account $accountId not found")

            val aNewSubscription = newSubscriptionWith(anUserAccount.id, aPlan)
            subscriptionGateway.save(aNewSubscription)

            object : Output {
                override val subscriptionId = aNewSubscription.id
            }
        }

    private fun validateActiveSubscription(accountId: String) {
        subscriptionGateway.latestSubscriptionOfAccount(accountId)?.let {
            if (!it.isCanceled()) {
                throw DomainException.with("Account $accountId already has an active subscription")
            }
        }
    }

    private fun newSubscriptionWith(
        accountId: AccountId,
        aPlan: Plan,
    ) = Subscription.new(
        anId = subscriptionGateway.nextId(),
        anAccountId = accountId,
        selectedPlan = aPlan,
    )
}
