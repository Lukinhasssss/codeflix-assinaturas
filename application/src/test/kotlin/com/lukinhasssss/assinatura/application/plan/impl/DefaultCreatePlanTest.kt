package com.lukinhasssss.assinatura.application.plan.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.plan.CreatePlan
import com.lukinhasssss.assinatura.domain.money.Money
import com.lukinhasssss.assinatura.domain.plan.PlanGateway
import com.lukinhasssss.assinatura.domain.plan.PlanId
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DefaultCreatePlanTest : UseCaseTest, FunSpec({
    val planGateway = mockk<PlanGateway>()

    val sut = DefaultCreatePlan(planGateway)

    test("given valid input, when calls execute, should create plan") {
        // given
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedPrice = 13.0
        val expectedCurrency = "USD"
        val expectedActive = true
        val expectedPlanId = PlanId("1234567890")

        val input =
            object : CreatePlan.Input {
                override val name = expectedName
                override val description = expectedDescription
                override val price = expectedPrice
                override val currency = expectedCurrency
                override val active = expectedActive
            }

        every { planGateway.nextId() } returns expectedPlanId
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
