package com.lukinhasssss.assinatura.domain.plan

interface PlanGateway {
    fun nextId(): PlanId

    fun planOfId(id: PlanId): Plan?

    fun allPlans(): List<Plan>

    fun existsPlanOfId(id: PlanId): Boolean

    fun save(plan: Plan): Plan
}
