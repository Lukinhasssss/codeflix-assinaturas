package com.lukinhasssss.assinatura.application.subscription.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.subscription.CreateSubscription
import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.plan.PlanGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionId
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DefaultCreateSubscriptionTest : UseCaseTest, FunSpec({
    val accountGateway = mockk<AccountGateway>()
    val planGateway = mockk<PlanGateway>()
    val subscriptionGateway = mockk<SubscriptionGateway>()

    val sut =
        DefaultCreateSubscription(
            subscriptionGateway,
            planGateway,
            accountGateway,
        )

    test("given valid account and aplan id, when calls execute, should return a new subscription") {
        // given
        val expectedPlan = Fixture.Plans.plus()
        val expectedAccount = Fixture.Accounts.john()
        val expectedSubscriptionId = SubscriptionId("SUB-123")

        val input =
            object : CreateSubscription.Input {
                override val accountId = expectedAccount.id.value
                override val planId = expectedPlan.id.value
            }

        every { subscriptionGateway.latestSubscriptionOfAccount(any()) } returns null
        every { planGateway.planOfId(any()) } returns expectedPlan
        every { accountGateway.accountOfId(any()) } returns expectedAccount
        every { subscriptionGateway.nextId() } returns expectedSubscriptionId
        every { subscriptionGateway.save(any()) } answers { firstArg() }

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.subscriptionId shouldBe expectedSubscriptionId

        verify {
            subscriptionGateway.save(
                withArg {
                    it.id shouldBe expectedSubscriptionId
                    it.accountId shouldBe expectedAccount.id
                    it.planId shouldBe expectedPlan.id
                    it.isTrial().shouldBeTrue()
                },
            )
        }
    }
})
