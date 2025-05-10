package com.lukinhasssss.assinatura.application.subscription

import com.lukinhasssss.assinatura.application.UseCase
import com.lukinhasssss.assinatura.domain.payment.Transaction
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId
import java.time.LocalDate

abstract class ChargeSubscription : UseCase<ChargeSubscription.Input, ChargeSubscription.Output>() {
    interface Input {
        val accountId: String
        val subscriptionId: String
        val paymentType: String
        val creditCardToken: String?
    }

    interface Output {
        val subscriptionId: SubscriptionId
        val subscriptionStatus: String
        val subscriptionDueDate: LocalDate
        val paymentTransaction: Transaction?
    }
}
