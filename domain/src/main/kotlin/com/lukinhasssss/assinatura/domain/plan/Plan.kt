package com.lukinhasssss.assinatura.domain.plan

import com.lukinhasssss.assinatura.domain.AggregateRoot
import com.lukinhasssss.assinatura.domain.money.Money
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import java.time.Instant

class Plan private constructor(
    planId: PlanId,
    var version: Int = 0,
    var name: String,
    var description: String,
    var isActive: Boolean = false,
    val price: Money,
    val createdAt: Instant = InstantUtils.now(),
    var updatedAt: Instant = InstantUtils.now(),
    var deletedAt: Instant?,
) : AggregateRoot<PlanId>(planId) {
    init {
        name = assertArgumentNotEmpty(name, "'name' should not be empty")
        name = assertArgumentMaxLength(name, MAX_NAME_LENGTH, "'name' should not be greater than 100 characters")
        description = assertArgumentNotEmpty(description, "'description' should not be empty")
        description = assertArgumentMaxLength(description, MAX_DESCRIPTION_LENGTH, "'description' should not be greater than 500 characters")
    }

    companion object {
        const val MAX_NAME_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 500

        fun newPlan(
            aPlanId: PlanId,
            aName: String,
            aDescription: String,
            isActive: Boolean = false,
            aPrice: Money,
        ): Plan {
            return Plan(
                planId = aPlanId,
                name = aName,
                description = aDescription,
                isActive = isActive,
                price = aPrice,
                deletedAt = if (isActive) null else InstantUtils.now(),
            )
        }

        fun with(
            aPlanId: PlanId,
            aVersion: Int,
            aName: String,
            aDescription: String,
            isActive: Boolean = false,
            aPrice: Money,
            createdAt: Instant = InstantUtils.now(),
            updatedAt: Instant = InstantUtils.now(),
            deletedAt: Instant? = null,
        ): Plan {
            return Plan(
                planId = aPlanId,
                version = aVersion,
                name = aName,
                description = aDescription,
                isActive = isActive,
                price = aPrice,
                createdAt = createdAt,
                updatedAt = updatedAt,
                deletedAt = deletedAt,
            )
        }
    }

    fun execute(vararg commands: PlanCommand) {
        if (commands.isEmpty()) return

        for (command in commands) {
            when (command) {
                is PlanCommand.ChangePlan -> changePlan(command)
                is PlanCommand.InactivatePlan -> inactivatePlan()
                is PlanCommand.ActivatePlan -> activatePlan()
            }
        }

        incrementVersion()
        updatedAt = InstantUtils.now()
    }

    private fun changePlan(command: PlanCommand.ChangePlan) {
        name = command.name
        description = command.description

        if (command.isActive) {
            activatePlan()
        } else {
            inactivatePlan()
        }
    }

    private fun inactivatePlan() {
        deletedAt = deletedAt ?: InstantUtils.now()
        isActive = false
    }

    private fun activatePlan() {
        deletedAt = null
        isActive = true
    }

    private fun incrementVersion() {
        version++
    }
}
