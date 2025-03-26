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

class IncompleteSubscriptionStatusTest : UnitTest, FunSpec({
    test("given two instances, when compared, should be equals") {
        // given
        val expectedEquals = true
        val one = IncompleteSubscriptionStatus(Subscription.new(SubscriptionId("SUB123"), AccountId("ACC123"), Fixture.Plans.plus()))
        val another = IncompleteSubscriptionStatus(Subscription.new(SubscriptionId("SUB456"), AccountId("ACC456"), Fixture.Plans.plus()))

        // when
        val actualEquals = one == another

        // then
        actualEquals shouldBe expectedEquals
    }

    test("given two instances, when calls hashCode, should be equals") {
        // given
        val one = IncompleteSubscriptionStatus(Subscription.new(SubscriptionId("SUB123"), AccountId("ACC123"), Fixture.Plans.plus()))
        val another = IncompleteSubscriptionStatus(Subscription.new(SubscriptionId("SUB456"), AccountId("ACC456"), Fixture.Plans.plus()))

        // then
        one.hashCode() shouldBe another.hashCode()
    }

    test("given incomplete status, when calls active, should transit to active status") {
        // given
        val expectedStatusClass = ActiveSubscriptionStatus::class
        val expectedSubscription = incompleteSubscription()
        val target = IncompleteSubscriptionStatus(expectedSubscription)

        // when
        target.active()

        // then
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given incomplete status, when calls cancel, should transit to canceled status") {
        // given
        val expectedStatusClass = CanceledSubscriptionStatus::class
        val expectedSubscription = incompleteSubscription()
        val target = IncompleteSubscriptionStatus(expectedSubscription)

        // when
        target.cancel()

        // then
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given incomplete status, when calls incomplete, should do nothing") {
        // given
        val expectedStatusClass = IncompleteSubscriptionStatus::class
        val expectedSubscription = incompleteSubscription()
        val target = IncompleteSubscriptionStatus(expectedSubscription)

        // when
        target.incomplete()

        // then
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given incomplete status, when calls trailing, should throw DomainException") {
        // given
        val expectedErrorMessage = "Subscription with status incomplete can't be changed to trailing"
        val expectedStatusClass = IncompleteSubscriptionStatus::class
        val expectedSubscription = incompleteSubscription()

        val target = IncompleteSubscriptionStatus(expectedSubscription)

        // when
        val actualError = shouldThrow<DomainException> { target.trailing() }

        // then
        actualError.message shouldBe expectedErrorMessage
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }
}) {
    private companion object {
        fun incompleteSubscription(): Subscription {
            val expectedSubscription =
                Subscription.new(
                    anId = SubscriptionId("SUB123"),
                    anAccountId = AccountId("ACC123"),
                    selectedPlan = Fixture.Plans.plus(),
                )
            expectedSubscription.status.incomplete()
            return expectedSubscription
        }
    }
}
