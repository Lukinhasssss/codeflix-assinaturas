package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.AggregateRoot
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.plan.Plan
import com.lukinhasssss.assinatura.domain.plan.PlanId
import com.lukinhasssss.assinatura.domain.subscription.status.ActiveSubscriptionStatus
import com.lukinhasssss.assinatura.domain.subscription.status.CanceledSubscriptionStatus
import com.lukinhasssss.assinatura.domain.subscription.status.IncompleteSubscriptionStatus
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus
import com.lukinhasssss.assinatura.domain.subscription.status.TrialingSubscriptionStatus
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import java.time.Instant
import java.time.LocalDate

class Subscription private constructor(
    subscriptionId: SubscriptionId,
    val version: Int = 0,
    val accountId: AccountId,
    val planId: PlanId,
    status: String,
    var dueDate: LocalDate,
    var lastRenewDate: Instant? = null,
    var lastTransactionId: String? = null,
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

            val aNewSubscription =
                Subscription(
                    subscriptionId = anId,
                    accountId = anAccountId,
                    planId = selectedPlan.id,
                    status = SubscriptionStatus.TRIALING,
                    dueDate = LocalDate.now().plusMonths(1),
                    createdAt = now,
                    updatedAt = now,
                )

            aNewSubscription.registerEvent(SubscriptionEvent.SubscriptionCreated(aNewSubscription))

            return aNewSubscription
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
                is SubscriptionCommand.IncompleteSubscription -> apply(command)
                is SubscriptionCommand.RenewSubscription -> apply(command)
                is SubscriptionCommand.CancelSubscription -> apply()
                is SubscriptionCommand.ChangeStatus -> apply(command)
            }
        }

        updatedAt = InstantUtils.now()
    }

    fun isTrial(): Boolean = status is TrialingSubscriptionStatus

    fun isActive(): Boolean = status is ActiveSubscriptionStatus

    fun isCanceled(): Boolean = status is CanceledSubscriptionStatus

    fun isIncomplete(): Boolean = status is IncompleteSubscriptionStatus

    fun isExpired(): Boolean = dueDate.isBefore(LocalDate.now())

    private fun apply(command: SubscriptionCommand.IncompleteSubscription) {
        status.incomplete()
        lastTransactionId = command.aTransactionId
        registerEvent(SubscriptionEvent.SubscriptionIncomplete(this, command.aReason))
    }

    private fun apply(command: SubscriptionCommand.RenewSubscription) {
        status.active()
        lastTransactionId = command.aTransactionId
        dueDate = dueDate.plusMonths(1)
        lastRenewDate = InstantUtils.now()
        registerEvent(SubscriptionEvent.SubscriptionRenewed(this, command.selectedPlan))
    }

    private fun apply() {
        status.cancel()
        registerEvent(SubscriptionEvent.SubscriptionCanceled(this))
    }

    private fun apply(command: SubscriptionCommand.ChangeStatus) {
        status = SubscriptionStatus.create(command.status, this)
    }
}
