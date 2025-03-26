package com.lukinhasssss.assinatura.domain.subscription.status

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.subscription.Subscription
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs

class TrailingSubscriptionStatusTest : UnitTest, FunSpec({
    test("given two instances, when compared, should be equals") {
        // given
        val expectedEquals = true
        val one = TrailingSubscriptionStatus(Subscription.new(SubscriptionId("SUB123"), AccountId("ACC123"), Fixture.Plans.plus()))
        val another = TrailingSubscriptionStatus(Subscription.new(SubscriptionId("SUB456"), AccountId("ACC456"), Fixture.Plans.plus()))

        // when
        val actualEquals = one == another

        // then
        actualEquals shouldBe expectedEquals
    }

    test("given two instances, when calls hashCode, should be equals") {
        // given
        val one = TrailingSubscriptionStatus(Subscription.new(SubscriptionId("SUB123"), AccountId("ACC123"), Fixture.Plans.plus()))
        val another = TrailingSubscriptionStatus(Subscription.new(SubscriptionId("SUB456"), AccountId("ACC456"), Fixture.Plans.plus()))

        // then
        one.hashCode() shouldBe another.hashCode()
    }

    test("given trailing status, when calls active, should transit to active status") {
        // given
        val expectedStatusClass = ActiveSubscriptionStatus::class
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )
        val target = TrailingSubscriptionStatus(expectedSubscription)

        // when
        target.active()

        // then
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given trailing status, when calls cancel, should transit to canceled status") {
        // given
        val expectedStatusClass = CanceledSubscriptionStatus::class
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )
        val target = TrailingSubscriptionStatus(expectedSubscription)

        // when
        target.cancel()

        // then
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given trailing status, when calls incomplete, should transit to incomplete status") {
        // given
        val expectedStatusClass = IncompleteSubscriptionStatus::class
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )
        val target = TrailingSubscriptionStatus(expectedSubscription)

        // when
        target.incomplete()

        // then
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given trailing status, when calls trailing, should do nothing") {
        // given
        val expectedStatusClass = TrailingSubscriptionStatus::class
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )
        val target = TrailingSubscriptionStatus(expectedSubscription)

        // when
        target.trailing()

        // then
        expectedSubscription.status::class shouldBeSameInstanceAs expectedStatusClass
    }
})
