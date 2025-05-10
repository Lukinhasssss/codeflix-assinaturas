package com.lukinhasssss.assinatura.domain.plan

import com.lukinhasssss.assinatura.domain.money.Money

sealed interface PlanCommand {
    data class ChangePlan(
        val name: String,
        val description: String,
        val price: Money,
        val isActive: Boolean,
    ) : PlanCommand

    class InactivatePlan : PlanCommand

    class ActivatePlan : PlanCommand
}
