package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.AssertionConcern
import com.lukinhasssss.assinatura.domain.plan.Plan

sealed interface SubscriptionCommand : AssertionConcern {
    data class IncompleteSubscription(
        val aReason: String,
        val aTransactionId: String,
    ) : SubscriptionCommand {
        init {
            assertArgumentNotEmpty(aTransactionId, "'transactionId' should not be empty")
        }
    }

    data class RenewSubscription(
        val selectedPlan: Plan,
        val aTransactionId: String,
    ) : SubscriptionCommand {
        init {
            assertArgumentNotEmpty(aTransactionId, "'transactionId' should not be empty")
        }
    }

    data class ChangeStatus(
        val status: String,
    ) : SubscriptionCommand {
        init {
            assertArgumentNotEmpty(status, "'status' should not be empty")
        }
    }
}
