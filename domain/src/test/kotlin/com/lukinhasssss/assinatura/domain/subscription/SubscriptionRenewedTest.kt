package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class SubscriptionRenewedTest : UnitTest, FunSpec({
    test("given empty subscriptionId, when instantiating SubscriptionRenewed, then should DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionRenewed(
                    subscriptionId = "",
                    accountId = "accountId",
                    planId = "planId",
                    transactionId = "transactionId",
                    currency = "USD",
                    amount = 100.0,
                    dueDate = LocalDate.now(),
                    renewedAt = InstantUtils.now(),
                    occurredOn = InstantUtils.now(),
                )
            }
        actualException.message shouldBe "'subscriptionId' should not be empty"
    }

    test("given empty accountId, when instantiating SubscriptionRenewed, then should DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionRenewed(
                    subscriptionId = "subscriptionId",
                    accountId = "",
                    planId = "planId",
                    transactionId = "transactionId",
                    currency = "USD",
                    amount = 100.0,
                    dueDate = LocalDate.now(),
                    renewedAt = InstantUtils.now(),
                    occurredOn = InstantUtils.now(),
                )
            }
        actualException.message shouldBe "'accountId' should not be empty"
    }

    test("given empty planId, when instantiating SubscriptionRenewed, then should DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionRenewed(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "",
                    transactionId = "transactionId",
                    currency = "USD",
                    amount = 100.0,
                    dueDate = LocalDate.now(),
                    renewedAt = InstantUtils.now(),
                    occurredOn = InstantUtils.now(),
                )
            }
        actualException.message shouldBe "'planId' should not be empty"
    }

    test("given empty transactionId, when instantiating SubscriptionRenewed, then should DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionRenewed(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "planId",
                    transactionId = "",
                    currency = "USD",
                    amount = 100.0,
                    dueDate = LocalDate.now(),
                    renewedAt = InstantUtils.now(),
                    occurredOn = InstantUtils.now(),
                )
            }
        actualException.message shouldBe "'transactionId' should not be empty"
    }

    test("given empty currency, when instantiating SubscriptionRenewed, then should DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionRenewed(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "planId",
                    transactionId = "transactionId",
                    currency = "",
                    amount = 100.0,
                    dueDate = LocalDate.now(),
                    renewedAt = InstantUtils.now(),
                    occurredOn = InstantUtils.now(),
                )
            }
        actualException.message shouldBe "'currency' should not be empty"
    }

    test("given zero amount, when instantiating SubscriptionRenewed, then should DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionRenewed(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "planId",
                    transactionId = "transactionId",
                    currency = "USD",
                    amount = 0.0,
                    dueDate = LocalDate.now(),
                    renewedAt = InstantUtils.now(),
                    occurredOn = InstantUtils.now(),
                )
            }
        actualException.message shouldBe "'amount' should be greater than 0"
    }

    test("given negative amount, when instantiating SubscriptionRenewed, then should DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionRenewed(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "planId",
                    transactionId = "transactionId",
                    currency = "USD",
                    amount = -100.0,
                    dueDate = LocalDate.now(),
                    renewedAt = InstantUtils.now(),
                    occurredOn = InstantUtils.now(),
                )
            }
        actualException.message shouldBe "'amount' should be greater than 0"
    }
})
