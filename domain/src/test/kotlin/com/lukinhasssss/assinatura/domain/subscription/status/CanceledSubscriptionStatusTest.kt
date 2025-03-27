package com.lukinhasssss.assinatura.domain.subscription.status

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs

class CanceledSubscriptionStatusTest : UnitTest, FunSpec({
    test("given two instances, when compared, should be equals") {
        // given
        val expectedEquals = true
        val one = CanceledSubscriptionStatus(Subscription.new(SubscriptionId("SUB123"), AccountId("ACC123"), Fixture.Plans.plus()))
        val another = CanceledSubscriptionStatus(Subscription.new(SubscriptionId("SUB456"), AccountId("ACC456"), Fixture.Plans.plus()))

        // when
        val actualEquals = one == another

        // then
        actualEquals shouldBe expectedEquals
    }

    test("given two instances, when calls hashCode, should be equals") {
        // given
        val one = CanceledSubscriptionStatus(Subscription.new(SubscriptionId("SUB123"), AccountId("ACC123"), Fixture.Plans.plus()))
        val another = CanceledSubscriptionStatus(Subscription.new(SubscriptionId("SUB456"), AccountId("ACC456"), Fixture.Plans.plus()))

        // then
        one.hashCode() shouldBe another.hashCode()
    }

    test("given canceled status, when calls active, should transit to active status") {
        // given
        val expectedErrorMessage = "Subscription with status canceled can't be changed to active"
        val expectedStatusClass = CanceledSubscriptionStatus::class
        val expectedSubscription = canceledSubscription()

        val target = CanceledSubscriptionStatus(expectedSubscription)

        // when
        val actualError = shouldThrow<DomainException> { target.active() }

        // then
        actualError.message shouldBe expectedErrorMessage
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given canceled status, when calls cancel, should do nothing") {
        // given
        val expectedStatusClass = CanceledSubscriptionStatus::class
        val expectedSubscription = canceledSubscription()
        val target = CanceledSubscriptionStatus(expectedSubscription)

        // when
        target.cancel()

        // then
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given canceled status, when calls incomplete, should throw DomainException") {
        // given
        val expectedErrorMessage = "Subscription with status canceled can't be changed to incomplete"
        val expectedStatusClass = CanceledSubscriptionStatus::class
        val expectedSubscription = canceledSubscription()

        val target = CanceledSubscriptionStatus(expectedSubscription)

        // when
        val actualError = shouldThrow<DomainException> { target.incomplete() }

        // then
        actualError.message shouldBe expectedErrorMessage
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given canceled status, when calls trialing, should throw DomainException") {
        // given
        val expectedErrorMessage = "Subscription with status canceled can't be changed to trialing"
        val expectedStatusClass = CanceledSubscriptionStatus::class
        val expectedSubscription = canceledSubscription()

        val target = CanceledSubscriptionStatus(expectedSubscription)

        // when
        val actualError = shouldThrow<DomainException> { target.trialing() }

        // then
        actualError.message shouldBe expectedErrorMessage
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }
}) {
    private companion object {
        fun canceledSubscription(): Subscription {
            val expectedSubscription =
                Subscription.new(
                    anId = SubscriptionId("SUB123"),
                    anAccountId = AccountId("ACC123"),
                    selectedPlan = Fixture.Plans.plus(),
                )
            expectedSubscription.status.cancel()
            return expectedSubscription
        }
    }
}
