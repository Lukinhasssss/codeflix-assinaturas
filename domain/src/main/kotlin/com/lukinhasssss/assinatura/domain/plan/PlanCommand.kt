package com.lukinhasssss.assinatura.domain.plan

sealed interface PlanCommand {
    data class ChangePlan(
        val name: String,
        val description: String,
        val isActive: Boolean,
    ) : PlanCommand

    class InactivatePlan : PlanCommand

    class ActivatePlan : PlanCommand
}
