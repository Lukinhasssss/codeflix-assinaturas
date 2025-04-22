package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.AssertionConcern
import com.lukinhasssss.assinatura.domain.plan.Plan

sealed interface SubscriptionCommand : AssertionConcern {
    class IncompleteSubscription(
        val aReason: String,
        val aTransactionId: String,
    ) : SubscriptionCommand {
        init {
            assertArgumentNotEmpty(aTransactionId, "'transactionId' should not be empty")
        }
    }

    class RenewSubscription(
        val selectedPlan: Plan,
        val aTransactionId: String,
    ) : SubscriptionCommand {
        init {
            assertArgumentNotEmpty(aTransactionId, "'transactionId' should not be empty")
        }
    }

    class CancelSubscription : SubscriptionCommand

    class ChangeStatus(
        val status: String,
    ) : SubscriptionCommand {
        init {
            assertArgumentNotEmpty(status, "'status' should not be empty")
        }
    }
}
