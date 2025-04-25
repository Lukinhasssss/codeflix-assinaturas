package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Instant
import java.time.LocalDate

class SubscriptionIncompleteTest : UnitTest, FunSpec({
    test("given empty subscriptionId, when instantiating SubscriptionIncomplete, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionIncomplete(
                    subscriptionId = "",
                    accountId = "accountId",
                    planId = "planId",
                    reason = "reason",
                    dueDate = LocalDate.now(),
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'subscriptionId' should not be empty"
    }

    test("given empty accountId, when instantiating SubscriptionIncomplete, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionIncomplete(
                    subscriptionId = "subscriptionId",
                    accountId = "",
                    planId = "planId",
                    reason = "reason",
                    dueDate = LocalDate.now(),
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'accountId' should not be empty"
    }

    test("given empty planId, when instantiating SubscriptionIncomplete, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionIncomplete(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "",
                    reason = "reason",
                    dueDate = LocalDate.now(),
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'planId' should not be empty"
    }

    test("given empty reason, when instantiating SubscriptionIncomplete, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionIncomplete(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "planId",
                    reason = "",
                    dueDate = LocalDate.now(),
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'aReason' should not be empty"
    }
})
