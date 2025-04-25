package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import jdk.internal.vm.vector.VectorSupport.test
import java.time.Instant

class SubscriptionCreatedTest : UnitTest, FunSpec({
    test("given empty subscriptionId, when instantiating SubscriptionCreated, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionCreated(
                    subscriptionId = "",
                    accountId = "accountId",
                    planId = "planId",
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'subscriptionId' should not be empty"
    }

    test("given empty accountId, when instantiating SubscriptionCreated, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionCreated(
                    subscriptionId = "subscriptionId",
                    accountId = "",
                    planId = "planId",
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'accountId' should not be empty"
    }

    test("given empty planId, when instantiating SubscriptionCreated, then should throw DomainException") {
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionEvent.SubscriptionCreated(
                    subscriptionId = "subscriptionId",
                    accountId = "accountId",
                    planId = "",
                    occurredOn = Instant.now(),
                )
            }
        actualException.message shouldBe "'planId' should not be empty"
    }
})
