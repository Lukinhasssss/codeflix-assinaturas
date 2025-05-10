package com.lukinhasssss.assinatura.application.subscription.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.subscription.CancelSubscription
import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class DefaultCancelSubscriptionTest : UseCaseTest, FunSpec({
    val subscriptionGateway = mockk<SubscriptionGateway>()

    val sut = DefaultCancelSubscription(subscriptionGateway)

    test("given active subscription, when calls cancel subscription, should cancel it") {
        // given
        val expectedPlan = Fixture.Plans.plus()
        val expectedAccount = Fixture.Accounts.john()
        val expectedSubscription =
            Fixture.Subscriptions.with(
                accountId = expectedAccount.id,
                planId = expectedPlan.id,
                status = SubscriptionStatus.ACTIVE,
                date = LocalDateTime.now().minusDays(15),
            )
        val expectedSubscriptionId = expectedSubscription.id
        val expectedSubscriptionStatus = SubscriptionStatus.CANCELED

        val input =
            object : CancelSubscription.Input {
                override val accountId = expectedAccount.id.value
                override val subscriptionId = expectedSubscription.id.value
            }

        every { subscriptionGateway.subscriptionOfId(any()) } returns expectedSubscription
        every { subscriptionGateway.save(any()) } answers { firstArg() }

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.subscriptionId shouldBe expectedSubscriptionId
        actualOutput.subscriptionStatus shouldBe expectedSubscriptionStatus
    }
})
