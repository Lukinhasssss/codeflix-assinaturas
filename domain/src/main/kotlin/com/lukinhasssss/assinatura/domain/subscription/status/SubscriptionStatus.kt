package com.lukinhasssss.assinatura.domain.subscription.status

import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionCommand
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus.Companion.ACTIVE
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus.Companion.CANCELED
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus.Companion.INCOMPLETE

sealed interface SubscriptionStatus {
    companion object {
        const val TRIALING = "trialing"
        const val INCOMPLETE = "incomplete"
        const val ACTIVE = "active"
        const val CANCELED = "canceled"

        fun create(
            status: String,
            aSubscription: Subscription,
        ): SubscriptionStatus =
            when (status) {
                TRIALING -> TrialingSubscriptionStatus(aSubscription)
                INCOMPLETE -> IncompleteSubscriptionStatus(aSubscription)
                ACTIVE -> ActiveSubscriptionStatus(aSubscription)
                CANCELED -> CanceledSubscriptionStatus(aSubscription)
                else -> throw DomainException.with("Invalid status: $status")
            }
    }

    fun value() =
        when (this) {
            is TrialingSubscriptionStatus -> TRIALING
            is IncompleteSubscriptionStatus -> INCOMPLETE
            is ActiveSubscriptionStatus -> ACTIVE
            is CanceledSubscriptionStatus -> CANCELED
        }

    fun trialing()

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
    override fun trialing() {}

    override fun incomplete() {}

    override fun active() {}

    override fun cancel() {}
}

data class TrialingSubscriptionStatus(
    val subscription: Subscription,
) : AbstractSubscriptionStatus() {
    override fun incomplete() {
        subscription.execute(SubscriptionCommand.ChangeStatus(INCOMPLETE))
    }

    override fun active() {
        subscription.execute(SubscriptionCommand.ChangeStatus(ACTIVE))
    }

    override fun cancel() {
        subscription.execute(SubscriptionCommand.ChangeStatus(CANCELED))
    }

    override fun equals(other: Any?) = other is TrialingSubscriptionStatus

    override fun hashCode(): Int = javaClass.hashCode()
}

data class IncompleteSubscriptionStatus(
    val subscription: Subscription,
) : AbstractSubscriptionStatus() {
    override fun trialing() {
        throw DomainException.with("Subscription with status incomplete can't be changed to trialing")
    }

    override fun active() {
        subscription.execute(SubscriptionCommand.ChangeStatus(ACTIVE))
    }

    override fun cancel() {
        subscription.execute(SubscriptionCommand.ChangeStatus(CANCELED))
    }

    override fun equals(other: Any?) = other is IncompleteSubscriptionStatus

    override fun hashCode(): Int = javaClass.hashCode()
}

data class ActiveSubscriptionStatus(
    val subscription: Subscription,
) : AbstractSubscriptionStatus() {
    override fun trialing() {
        throw DomainException.with("Subscription with status active can't be changed to trialing")
    }

    override fun incomplete() {
        subscription.execute(SubscriptionCommand.ChangeStatus(INCOMPLETE))
    }

    override fun cancel() {
        subscription.execute(SubscriptionCommand.ChangeStatus(CANCELED))
    }

    override fun equals(other: Any?) = other is ActiveSubscriptionStatus

    override fun hashCode(): Int = javaClass.hashCode()
}

data class CanceledSubscriptionStatus(
    val subscription: Subscription,
) : AbstractSubscriptionStatus() {
    override fun trialing() {
        throw DomainException.with("Subscription with status canceled can't be changed to trialing")
    }

    override fun incomplete() {
        throw DomainException.with("Subscription with status canceled can't be changed to incomplete")
    }

    override fun active() {
        throw DomainException.with("Subscription with status canceled can't be changed to active")
    }

    override fun equals(other: Any?) = other is CanceledSubscriptionStatus

    override fun hashCode(): Int = javaClass.hashCode()
}
