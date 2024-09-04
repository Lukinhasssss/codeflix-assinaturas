package com.lukinhasssss.assinatura.domain.plan

import com.lukinhasssss.admin.catalogo.domain.AggregateRoot
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import java.time.Instant

class Plan private constructor(
    planId: PlanId,
    val version: Int = 0,
    var name: String,
    var description: String,
    val isActive: Boolean = false,
    val price: MonetaryAmount,
    val createdAt: Instant = InstantUtils.now(),
    val updatedAt: Instant = InstantUtils.now(),
    val deletedAt: Instant?,
) : AggregateRoot<PlanId>(planId) {
    init {
        name = assertArgumentNotEmpty(name, "'name' should not be empty")
        name = assertArgumentMaxLength(name, 100, "'name' should not be greater than 100 characters")
        description = assertArgumentNotEmpty(description, "'description' should not be empty")
        description = assertArgumentMaxLength(description, 500, "'description' should not be greater than 500 characters")
    }

    companion object {
        fun newPlan(
            aPlanId: PlanId,
            aName: String,
            aDescription: String,
            isActive: Boolean = false,
            aPrice: MonetaryAmount,
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
            aPrice: MonetaryAmount,
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
}
