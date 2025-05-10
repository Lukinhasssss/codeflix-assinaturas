package com.lukinhasssss.assinatura.application.subscription.impl

import com.lukinhasssss.assinatura.application.subscription.ChargeSubscription
import com.lukinhasssss.assinatura.domain.account.Account
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.payment.BillingAddress
import com.lukinhasssss.assinatura.domain.payment.Payment
import com.lukinhasssss.assinatura.domain.payment.PaymentGateway
import com.lukinhasssss.assinatura.domain.person.Address
import com.lukinhasssss.assinatura.domain.plan.Plan
import com.lukinhasssss.assinatura.domain.plan.PlanGateway
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionCommand
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class DefaultChargeSubscription(
    private val accountGateway: AccountGateway,
    private val clock: Clock,
    private val paymentGateway: PaymentGateway,
    private val planGateway: PlanGateway,
    private val subscriptionGateway: SubscriptionGateway,
) : ChargeSubscription() {
    private companion object {
        const val MAX_INCOMPLETE_DAYS = 2
    }

    override fun execute(input: Input): Output {
        val now = clock.instant()
        val accountId = AccountId(input.accountId)
        val subscriptionId = SubscriptionId(input.subscriptionId)

        val aSubscription =
            subscriptionGateway.subscriptionOfId(subscriptionId)
                ?.takeIf { it.accountId == accountId }
                ?: throw DomainException.notFound(Subscription::class, subscriptionId)

        if (aSubscription.dueDate.isAfter(LocalDate.ofInstant(now, ZoneId.systemDefault()))) {
            return object : Output {
                override val subscriptionId = subscriptionId
                override val subscriptionStatus = aSubscription.status.value()
                override val subscriptionDueDate = aSubscription.dueDate
                override val paymentTransaction = null
            }
        }

        val aPlan =
            planGateway.planOfId(aSubscription.planId)
                ?: throw DomainException.notFound(Plan::class, aSubscription.planId)

        val anUserAccount =
            accountGateway.accountOfId(accountId)
                ?: throw DomainException.notFound(Account::class, accountId)

        // Sera que o address deveria ser nulo nesse ponto ?
        val aPayment = newPaymentWith(input, aPlan, anUserAccount.billingAddress!!)
        val actualTransaction = paymentGateway.processPayment(aPayment)

        if (actualTransaction.isSuccess()) {
            aSubscription.execute(
                SubscriptionCommand.RenewSubscription(
                    selectedPlan = aPlan,
                    aTransactionId = actualTransaction.transactionId,
                ),
            )
        } else if (hasTolerableDays(aSubscription.dueDate, now)) {
            aSubscription.execute(
                SubscriptionCommand.IncompleteSubscription(
                    aReason = actualTransaction.errorMessage!!,
                    aTransactionId = actualTransaction.transactionId,
                ),
            )
        } else {
            aSubscription.execute(SubscriptionCommand.CancelSubscription())
        }

        subscriptionGateway.save(aSubscription)

        return object : Output {
            override val subscriptionId = subscriptionId
            override val subscriptionStatus = aSubscription.status.value()
            override val subscriptionDueDate = aSubscription.dueDate
            override val paymentTransaction = actualTransaction
        }
    }

    private fun hasTolerableDays(
        dueDate: LocalDate,
        now: Instant,
    ): Boolean = ChronoUnit.DAYS.between(dueDate, LocalDate.ofInstant(now, ZoneOffset.UTC)) <= MAX_INCOMPLETE_DAYS

    private fun newPaymentWith(
        input: Input,
        aPlan: Plan,
        address: Address,
    ) = Payment.create(
        type = input.paymentType,
        orderId = IdUtils.uuid(),
        amount = aPlan.price.amount,
        address =
            BillingAddress(
                zipCode = address.zipCode,
                number = address.number,
                complement = address.complement,
                country = address.country,
            ),
        token = input.creditCardToken,
    )
}
