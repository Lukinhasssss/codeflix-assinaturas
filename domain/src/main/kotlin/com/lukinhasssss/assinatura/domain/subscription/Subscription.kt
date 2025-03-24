package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.AggregateRoot
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.plan.Plan
import com.lukinhasssss.assinatura.domain.plan.PlanId
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import java.time.Instant
import java.time.LocalDate

class Subscription private constructor(
    subscriptionId: SubscriptionId,
    val version: Int = 0,
    val accountId: AccountId,
    val planId: PlanId,
    status: String,
    val dueDate: LocalDate,
    val lastRenewDate: Instant? = null,
    val lastTransactionId: String? = null,
    val createdAt: Instant = InstantUtils.now(),
    var updatedAt: Instant = InstantUtils.now(),
) : AggregateRoot<SubscriptionId>(subscriptionId) {
    var status: SubscriptionStatus = SubscriptionStatus.create(status, this)
        private set

    companion object {
        fun new(
            anId: SubscriptionId,
            anAccountId: AccountId,
            selectedPlan: Plan,
        ): Subscription {
            val now = InstantUtils.now()

            return Subscription(
                subscriptionId = anId,
                accountId = anAccountId,
                planId = selectedPlan.id,
                status = SubscriptionStatus.TRAILING,
                dueDate = LocalDate.now().plusMonths(1),
                createdAt = now,
                updatedAt = now,
            )
        }

        fun with(
            subscriptionId: SubscriptionId,
            version: Int,
            accountId: AccountId,
            planId: PlanId,
            status: String,
            dueDate: LocalDate,
            lastRenewDate: Instant? = null,
            lastTransactionId: String? = null,
            createdAt: Instant,
            updatedAt: Instant,
        ): Subscription {
            return Subscription(
                subscriptionId = subscriptionId,
                version = version,
                accountId = accountId,
                planId = planId,
                status = status,
                dueDate = dueDate,
                lastRenewDate = lastRenewDate,
                lastTransactionId = lastTransactionId,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }

    fun execute(vararg commands: SubscriptionCommand) {
        if (commands.isEmpty()) return

        commands.forEach { command ->
            when (command) {
                is SubscriptionCommand.ChangeStatus -> apply(command)
            }
        }

        updatedAt = InstantUtils.now()
    }

    private fun apply(command: SubscriptionCommand.ChangeStatus) {
        status = command.status
    }
}
