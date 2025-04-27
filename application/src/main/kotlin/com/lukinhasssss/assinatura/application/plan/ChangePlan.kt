package com.lukinhasssss.assinatura.application.plan

import com.lukinhasssss.assinatura.application.UseCase
import com.lukinhasssss.assinatura.domain.plan.PlanId

abstract class ChangePlan : UseCase<ChangePlan.Input, ChangePlan.Output>() {
    interface Input {
        val planId: String
        val name: String
        val description: String
        val currency: String
        val price: Double
        val active: Boolean
    }

    interface Output {
        val planId: PlanId
    }
}
