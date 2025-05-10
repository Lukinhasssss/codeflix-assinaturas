package com.lukinhasssss.assinatura.domain.plan

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.money.Money
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class PlanTest : UnitTest, FunSpec({
    test("given valid params, when calls new plan, should instantiate") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)

        // when
        val actualPlan =
            Plan.newPlan(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedPrice,
            )

        // then
        with(actualPlan) {
            id shouldBe expectedId
            version shouldBe expectedVersion
            name shouldBe expectedName
            description shouldBe expectedDescription
            isActive shouldBe expectedIsActive
            price shouldBe expectedPrice
            createdAt.shouldNotBeNull()
            updatedAt.shouldNotBeNull()
            deletedAt.shouldBeNull()
        }
    }

    test("given valid params, when calls with, should instantiate") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualPlan =
            Plan.with(
                expectedId,
                expectedVersion,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedPrice,
                expectedCreatedAt,
                expectedUpdatedAt,
                expectedDeletedAt,
            )

        // then
        with(actualPlan) {
            id shouldBe expectedId
            version shouldBe expectedVersion
            name shouldBe expectedName
            description shouldBe expectedDescription
            isActive shouldBe expectedIsActive
            price shouldBe expectedPrice
            createdAt shouldBe expectedCreatedAt
            updatedAt shouldBe expectedUpdatedAt
            deletedAt shouldBe expectedDeletedAt
        }
    }

    test("given an invalid planId, when call with, should return error") {
        // given
        val expectedErrorMessage = "'planId' should not be empty"
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Plan.with(
                    PlanId(""),
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt,
                )
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given an invalid name, when call with, should return error") {
        // given
        val expectedErrorMessage = "'name' should not be empty"
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = ""
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt,
                )
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given a name greater than 100 characters, when call with, should return error") {
        // given
        val expectedErrorMessage = "'name' should not be greater than 100 characters"
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "a".repeat(101)
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt,
                )
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given an invalid description, when call with, should return error") {
        // given
        val expectedErrorMessage = "'description' should not be empty"
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = ""
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt,
                )
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given a description greater than 500 characters, when call with, should return error") {
        // given
        val expectedErrorMessage = "'description' should not be greater than 500 characters"
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "a".repeat(501)
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt,
                )
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given isActive is not informed, when call with, should return false") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = false
        val expectedPrice = Money(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualPlan =
            Plan.with(
                aPlanId = expectedId,
                aVersion = expectedVersion,
                aName = expectedName,
                aDescription = expectedDescription,
                aPrice = expectedPrice,
                createdAt = expectedCreatedAt,
                updatedAt = expectedUpdatedAt,
                deletedAt = expectedDeletedAt,
            )

        // then
        actualPlan.isActive shouldBe expectedIsActive
    }

    test("given 0 as price, when call with, should return OK") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 0.0)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualPlan =
            Plan.with(
                expectedId,
                expectedVersion,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedPrice,
                expectedCreatedAt,
                expectedUpdatedAt,
                expectedDeletedAt,
            )

        // then
        actualPlan.price shouldBe expectedPrice
    }

    test("given a negative price, when call with, should return error") {
        // given
        val expectedErrorMessage = "'amount' should be greater than or equal to 0"
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    Money(Fixture.currency(), -32.90),
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt,
                )
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given deletedAt is not informed, when call with, should return null") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt: LocalDateTime? = null

        // when
        val actualPlan =
            Plan.with(
                expectedId,
                expectedVersion,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedPrice,
                expectedCreatedAt,
                expectedUpdatedAt,
            )

        // then
        actualPlan.deletedAt shouldBe expectedDeletedAt
    }

    test("given active plan, when execute inactivate command, should inactivate") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = false
        val expectedPrice = Money(Fixture.currency(), 32.90)

        val actualPlan = Plan.newPlan(expectedId, expectedName, expectedDescription, true, expectedPrice)
        actualPlan.isActive.shouldBeTrue()
        actualPlan.deletedAt.shouldBeNull()

        // when
        Thread.sleep(1)
        actualPlan.execute(PlanCommand.InactivatePlan())

        // then
        with(actualPlan) {
            id shouldBe expectedId
            version shouldBe expectedVersion
            name shouldBe expectedName
            description shouldBe expectedDescription
            isActive shouldBe expectedIsActive
            price shouldBe expectedPrice
            createdAt.shouldNotBeNull()
            updatedAt.isAfter(createdAt).shouldBeTrue()
            deletedAt.shouldNotBeNull()
        }
    }

    test("given inactive plan, when execute activate command, should activate") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)

        val actualPlan = Plan.newPlan(expectedId, expectedName, expectedDescription, false, expectedPrice)
        actualPlan.isActive.shouldBeFalse()
        actualPlan.deletedAt.shouldNotBeNull()

        // when
        Thread.sleep(1)
        actualPlan.execute(PlanCommand.ActivatePlan())

        // then
        with(actualPlan) {
            id shouldBe expectedId
            version shouldBe expectedVersion
            name shouldBe expectedName
            description shouldBe expectedDescription
            isActive shouldBe expectedIsActive
            price shouldBe expectedPrice
            createdAt.shouldNotBeNull()
            updatedAt.isAfter(createdAt).shouldBeTrue()
            deletedAt.shouldBeNull()
        }
    }

    test("given a plan, when execute change plan command, should update attributes") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)

        val actualPlan = Plan.newPlan(expectedId, expectedName, expectedDescription, expectedIsActive, expectedPrice)

        val expectedNewName = "Premium"
        val expectedNewDescription = "The best plan ever"
        val expectedNewPrice = Money(Fixture.currency(), 13.90)
        val expectedNewIsActive = false

        // when
        Thread.sleep(1)
        actualPlan.execute(
            PlanCommand.ChangePlan(
                expectedNewName,
                expectedNewDescription,
                expectedNewPrice,
                expectedNewIsActive,
            ),
        )

        // then
        with(actualPlan) {
            id shouldBe expectedId
            version shouldBe expectedVersion
            name shouldBe expectedNewName
            description shouldBe expectedNewDescription
            isActive shouldBe expectedNewIsActive
            price shouldBe expectedNewPrice
            createdAt.shouldNotBeNull()
            updatedAt.isAfter(createdAt).shouldBeTrue()
            deletedAt.shouldNotBeNull()
        }
    }

    test("given a plan, when execute without commands, should do nothing") {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = Money(Fixture.currency(), 32.90)

        val actualPlan = Plan.newPlan(expectedId, expectedName, expectedDescription, expectedIsActive, expectedPrice)

        // when
        actualPlan.execute()

        // then
        with(actualPlan) {
            id shouldBe expectedId
            version shouldBe expectedVersion
            name shouldBe expectedName
            description shouldBe expectedDescription
            isActive shouldBe expectedIsActive
            price shouldBe expectedPrice
            createdAt.shouldNotBeNull()
            updatedAt.shouldNotBeNull()
            deletedAt.shouldBeNull()
        }
    }
})
