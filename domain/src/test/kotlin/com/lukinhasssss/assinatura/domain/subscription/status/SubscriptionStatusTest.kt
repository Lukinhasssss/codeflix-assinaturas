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

class SubscriptionStatusTest : UnitTest, FunSpec({
    test("given trailing status, when calls create, should instantiate TrailingSubscriptionStatus") {
        // given
        val expectedStatus = "trailing"
        val expectedStatusClass = TrailingSubscriptionStatus::class
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )

        // when
        val actualStatus = SubscriptionStatus.create(expectedStatus, expectedSubscription)

        // then
        actualStatus.value() shouldBe expectedStatus
        actualStatus::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given incomplete status, when calls create, should instantiate IncompleteSubscriptionStatus") {
        // given
        val expectedStatus = "incomplete"
        val expectedStatusClass = IncompleteSubscriptionStatus::class
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )

        // when
        val actualStatus = SubscriptionStatus.create(expectedStatus, expectedSubscription)

        // then
        actualStatus.value() shouldBe expectedStatus
        actualStatus::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given active status, when calls create, should instantiate ActiveSubscriptionStatus") {
        // given
        val expectedStatus = "active"
        val expectedStatusClass = ActiveSubscriptionStatus::class
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )

        // when
        val actualStatus = SubscriptionStatus.create(expectedStatus, expectedSubscription)

        // then
        actualStatus.value() shouldBe expectedStatus
        actualStatus::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given canceled status, when calls create, should instantiate CanceledSubscriptionStatus") {
        // given
        val expectedStatus = "canceled"
        val expectedStatusClass = CanceledSubscriptionStatus::class
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )

        // when
        val actualStatus = SubscriptionStatus.create(expectedStatus, expectedSubscription)

        // then
        actualStatus.value() shouldBe expectedStatus
        actualStatus::class shouldBeSameInstanceAs expectedStatusClass
    }

    test("given unexpected status, when calls create, should DomainException") {
        // given
        val expectedStatus = "unexpected"
        val expectedErrorMessage = "Invalid status: $expectedStatus"
        val expectedSubscription =
            Subscription.new(
                anId = SubscriptionId("SUB123"),
                anAccountId = AccountId("ACC123"),
                selectedPlan = Fixture.Plans.plus(),
            )

        // when
        val actualException =
            shouldThrow<DomainException> {
                SubscriptionStatus.create(expectedStatus, expectedSubscription)
            }

        // then
        actualException.message shouldBe expectedErrorMessage
    }
})
