package com.lukinhasssss.assinatura.application.plan.impl

import com.lukinhasssss.assinatura.application.plan.ChangePlan
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.money.Money
import com.lukinhasssss.assinatura.domain.plan.Plan
import com.lukinhasssss.assinatura.domain.plan.PlanCommand
import com.lukinhasssss.assinatura.domain.plan.PlanGateway
import com.lukinhasssss.assinatura.domain.plan.PlanId

class DefaultChangePlan(
    private val planGateway: PlanGateway,
) : ChangePlan() {
    override fun execute(input: Input): Output {
        val aPlanId = PlanId(input.planId)

        val aPlan =
            planGateway.planOfId(aPlanId)
                ?: throw DomainException.notFound(Plan::class, aPlanId)

        aPlan.execute(
            PlanCommand.ChangePlan(
                input.name,
                input.description,
                Money(input.currency, input.price),
                input.active,
            ),
        )

        planGateway.save(aPlan)

        return object : Output {
            override val planId = aPlanId
        }
    }
}
