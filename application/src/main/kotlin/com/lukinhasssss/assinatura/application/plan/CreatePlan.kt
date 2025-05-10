package com.lukinhasssss.assinatura.application.plan

import com.lukinhasssss.assinatura.application.UseCase
import com.lukinhasssss.assinatura.domain.plan.PlanId

abstract class CreatePlan : UseCase<CreatePlan.Input, CreatePlan.Output>() {
    interface Input {
        val name: String
        val description: String
        val price: Double
        val currency: String
        val active: Boolean
    }

    interface Output {
        val planId: PlanId
    }
}
