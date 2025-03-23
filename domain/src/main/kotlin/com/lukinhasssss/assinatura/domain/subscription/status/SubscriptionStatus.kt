package com.lukinhasssss.assinatura.domain.subscription.status

import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.subscription.Subscription

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
                else -> throw DomainException.with("Invalid status: $status")
            }
    }

    fun trailing()

    fun incomplete()

    fun active()

    fun cancel()

    fun value() =
        when (this) {
            is TrailingSubscriptionStatus -> TRAILING
        }
}
