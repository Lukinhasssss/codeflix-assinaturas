package com.lukinhasssss.assinatura.domain.subscription.status

import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionCommand

sealed interface SubscriptionStatus {
    companion object {
        const val TRAILING = "trailing"
        const val INCOMPLETE = "incomplete"
        const val ACTIVE = "active"
        const val CANCELED = "canceled"

        fun create(
            status: String,
            aSubscription: Subscription,
        ): SubscriptionStatus =
            when (status) {
                TRAILING -> TrailingSubscriptionStatus(aSubscription)
                INCOMPLETE -> IncompleteSubscriptionStatus(aSubscription)
                ACTIVE -> ActiveSubscriptionStatus(aSubscription)
                CANCELED -> CanceledSubscriptionStatus(aSubscription)
                else -> throw DomainException.with("Invalid status: $status")
            }
    }

    fun value() =
        when (this) {
            is TrailingSubscriptionStatus -> TRAILING
            is IncompleteSubscriptionStatus -> INCOMPLETE
            is ActiveSubscriptionStatus -> ACTIVE
            is CanceledSubscriptionStatus -> CANCELED
        }

    fun trailing()

    fun incomplete()

    fun active()

    fun cancel()
}

/**
 * No Kotlin, o equivalente ao permits do Java
 * (usado para definir quais classes podem implementar uma sealed interface ou sealed class)
 * é simplesmente declarar as classes no mesmo arquivo.
 */

sealed class AbstractSubscriptionStatus : SubscriptionStatus {
    override fun trailing() {}

    override fun incomplete() {}

    override fun active() {}

    override fun cancel() {}
}

data class TrailingSubscriptionStatus(
    val subscription: Subscription,
) : AbstractSubscriptionStatus() {
    override fun incomplete() {
        subscription.execute(SubscriptionCommand.ChangeStatus(IncompleteSubscriptionStatus(subscription)))
    }

    override fun active() {
        subscription.execute(SubscriptionCommand.ChangeStatus(ActiveSubscriptionStatus(subscription)))
    }

    override fun cancel() {
        subscription.execute(SubscriptionCommand.ChangeStatus(CanceledSubscriptionStatus(subscription)))
    }
}

data class IncompleteSubscriptionStatus(
    val subscription: Subscription,
) : AbstractSubscriptionStatus() {
    override fun trailing() {
        throw DomainException.with("Subscription with status incomplete can't be changed to trailing")
    }

    override fun active() {
        subscription.execute(SubscriptionCommand.ChangeStatus(ActiveSubscriptionStatus(subscription)))
    }

    override fun cancel() {
        subscription.execute(SubscriptionCommand.ChangeStatus(CanceledSubscriptionStatus(subscription)))
    }
}

data class ActiveSubscriptionStatus(
    val subscription: Subscription,
) : AbstractSubscriptionStatus() {
    override fun trailing() {
        throw DomainException.with("Subscription with status active can't be changed to trailing")
    }

    override fun incomplete() {
        subscription.execute(SubscriptionCommand.ChangeStatus(IncompleteSubscriptionStatus(subscription)))
    }

    override fun cancel() {
        subscription.execute(SubscriptionCommand.ChangeStatus(CanceledSubscriptionStatus(subscription)))
    }
}

data class CanceledSubscriptionStatus(
    val subscription: Subscription,
) : AbstractSubscriptionStatus() {
    override fun trailing() {
        throw DomainException.with("Subscription with status canceled can't be changed to trailing")
    }

    override fun incomplete() {
        throw DomainException.with("Subscription with status canceled can't be changed to incomplete")
    }

    override fun active() {
        throw DomainException.with("Subscription with status canceled can't be changed to active")
    }
}
