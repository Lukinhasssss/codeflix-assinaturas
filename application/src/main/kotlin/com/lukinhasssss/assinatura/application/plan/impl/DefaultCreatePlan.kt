package com.lukinhasssss.assinatura.application.plan.impl

import com.lukinhasssss.assinatura.application.plan.CreatePlan
import com.lukinhasssss.assinatura.domain.money.Money
import com.lukinhasssss.assinatura.domain.plan.Plan
import com.lukinhasssss.assinatura.domain.plan.PlanGateway

class DefaultCreatePlan(
    private val planGateway: PlanGateway,
) : CreatePlan() {
    override fun execute(input: Input): Output {
        val aPlan = input.toNewPlan()

        planGateway.save(aPlan)

        return object : Output {
            override val planId = aPlan.id
        }
    }

    private fun Input.toNewPlan() =
        Plan.newPlan(
            aPlanId = planGateway.nextId(),
            aName = name,
            aDescription = description,
            isActive = active,
            aPrice = Money(currency, price),
        )
}
