package com.lukinhasssss.assinatura.application.plan.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.plan.ChangePlan
import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.money.Money
import com.lukinhasssss.assinatura.domain.plan.PlanGateway
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DefaultChangePlanTest : UseCaseTest, FunSpec({
    val planGateway = mockk<PlanGateway>()

    val sut = DefaultChangePlan(planGateway)

    test("given valid input, when calls execute, should change plan") {
        // given
        val plan = Fixture.Plans.plus()
        val expectedName = "Master"
        val expectedDescription = "Better plan"
        val expectedPrice = 26.0
        val expectedCurrency = "USD"
        val expectedActive = true
        val expectedPlanId = plan.id

        val input =
            object : ChangePlan.Input {
                override val planId = expectedPlanId.value
                override val name = expectedName
                override val description = expectedDescription
                override val price = expectedPrice
                override val currency = expectedCurrency
                override val active = expectedActive
            }

        every { planGateway.planOfId(any()) } returns plan
        every { planGateway.save(any()) } answers { firstArg() }

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.planId shouldBeEqual expectedPlanId

        verify {
            planGateway.save(
                withArg {
                    it.id shouldBeEqual expectedPlanId
                    it.name shouldBeEqual expectedName
                    it.description shouldBeEqual expectedDescription
                    it.price shouldBeEqual Money(expectedCurrency, expectedPrice)
                    it.isActive shouldBeEqual expectedActive
                },
            )
        }
    }
})
