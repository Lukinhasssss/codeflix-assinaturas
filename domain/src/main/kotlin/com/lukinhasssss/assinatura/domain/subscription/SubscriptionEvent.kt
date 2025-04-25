package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.DomainEvent
import com.lukinhasssss.assinatura.domain.plan.Plan
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import java.time.Instant
import java.time.LocalDate

sealed interface SubscriptionEvent : DomainEvent {
    companion object {
        val TYPE = Subscription::class.simpleName!!
    }

    val subscriptionId: String

    override val aggregateId: String
        get() = subscriptionId

    override val aggregateType: String
        get() = TYPE

    data class SubscriptionCreated(
        override val subscriptionId: String,
        val accountId: String,
        val planId: String,
        override val occurredOn: Instant,
    ) : SubscriptionEvent {
        init {
            assertArgumentNotEmpty(subscriptionId, "'subscriptionId' should not be empty")
            assertArgumentNotEmpty(accountId, "'accountId' should not be empty")
            assertArgumentNotEmpty(planId, "'planId' should not be empty")
        }

        constructor(aSubscription: Subscription) : this(
            subscriptionId = aSubscription.id.value,
            accountId = aSubscription.accountId.value,
            planId = aSubscription.planId.value,
            occurredOn = InstantUtils.now(),
        )
    }

    data class SubscriptionIncomplete(
        override val subscriptionId: String,
        val accountId: String,
        val planId: String,
        val reason: String,
        val dueDate: LocalDate,
        override val occurredOn: Instant,
    ) : SubscriptionEvent {
        init {
            assertArgumentNotEmpty(subscriptionId, "'subscriptionId' should not be empty")
            assertArgumentNotEmpty(accountId, "'accountId' should not be empty")
            assertArgumentNotEmpty(planId, "'planId' should not be empty")
            assertArgumentNotEmpty(reason, "'aReason' should not be empty")
        }

        constructor(aSubscription: Subscription, aReason: String) : this(
            subscriptionId = aSubscription.id.value,
            accountId = aSubscription.accountId.value,
            planId = aSubscription.planId.value,
            reason = aReason,
            dueDate = aSubscription.dueDate,
            occurredOn = InstantUtils.now(),
        )
    }

    data class SubscriptionRenewed(
        override val subscriptionId: String,
        val accountId: String,
        val planId: String,
        val transactionId: String,
        val currency: String,
        val amount: Double,
        val dueDate: LocalDate,
        val renewedAt: Instant,
        override val occurredOn: Instant,
    ) : SubscriptionEvent {
        init {
            assertArgumentNotEmpty(subscriptionId, "'subscriptionId' should not be empty")
            assertArgumentNotEmpty(accountId, "'accountId' should not be empty")
            assertArgumentNotEmpty(planId, "'planId' should not be empty")
            assertArgumentNotEmpty(transactionId, "'transactionId' should not be empty")
            assertArgumentNotEmpty(currency, "'currency' should not be empty")
            assertConditionTrue(amount > 0, "'amount' should be greater than 0")
        }

        constructor(aSubscription: Subscription, selectedPlan: Plan) : this(
            subscriptionId = aSubscription.id.value,
            accountId = aSubscription.accountId.value,
            planId = aSubscription.planId.value,
            transactionId = aSubscription.lastTransactionId!!,
            currency = selectedPlan.price.currency.currencyCode,
            amount = selectedPlan.price.amount,
            dueDate = aSubscription.dueDate,
            renewedAt = aSubscription.lastRenewDate!!,
            occurredOn = InstantUtils.now(),
        )
    }

    data class SubscriptionCanceled(
        override val subscriptionId: String,
        val accountId: String,
        val planId: String,
        val dueDate: LocalDate,
        val renewedAt: Instant,
        override val occurredOn: Instant,
    ) : SubscriptionEvent {
        init {
            assertArgumentNotEmpty(subscriptionId, "'subscriptionId' should not be empty")
            assertArgumentNotEmpty(accountId, "'accountId' should not be empty")
            assertArgumentNotEmpty(planId, "'planId' should not be empty")
        }

        constructor(aSubscription: Subscription) : this(
            subscriptionId = aSubscription.id.value,
            accountId = aSubscription.accountId.value,
            planId = aSubscription.planId.value,
            dueDate = aSubscription.dueDate,
            renewedAt = aSubscription.lastRenewDate!!,
            occurredOn = InstantUtils.now(),
        )
    }
}
