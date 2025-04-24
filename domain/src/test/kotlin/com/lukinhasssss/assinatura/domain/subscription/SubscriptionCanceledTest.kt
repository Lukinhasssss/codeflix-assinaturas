package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Instant
import java.time.LocalDate

class SubscriptionCanceledTest : UnitTest, FunSpec({
    test("given empty subscriptioniId, when instantiating SubscriptionCanceled, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionCanceled(
                    subscriptionId = "",
                    accountId = "accountId",
                    planId = "planId",
                    dueDate = LocalDate.now(),
                    renewedAt = Instant.now(),
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'subscriptionId' should not be empty"
    }

    test("given empty accountId, when instantiating SubscriptionCanceled, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionCanceled(
                    subscriptionId = "subscriptionId",
                    accountId = "",
                    planId = "planId",
                    dueDate = LocalDate.now(),
                    renewedAt = Instant.now(),
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'accountId' should not be empty"
    }

    test("given empty planId, when instantiating SubscriptionCanceled, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionCanceled(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "",
                    dueDate = LocalDate.now(),
                    renewedAt = Instant.now(),
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'planId' should not be empty"
    }
})
