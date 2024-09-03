package com.lukinhasssss.assinatura.domain.plan

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class PlanTest : UnitTest {
    @Test
    fun `given valid params, when calls new plan, should instantiate`() {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)

        // when
        val actualPlan =
            Plan.newPlan(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedPrice
            )

        // then
        with(actualPlan) {
            assertEquals(expectedId, id)
            assertEquals(expectedVersion, version)
            assertEquals(expectedName, name)
            assertEquals(expectedDescription, description)
            assertEquals(expectedIsActive, isActive)
            assertEquals(expectedPrice, price)
            assertNotNull(createdAt)
            assertNotNull(updatedAt)
            assertNull(deletedAt)
        }
    }

    @Test
    fun `given valid params, when calls with, should instantiate`() {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)
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
                expectedDeletedAt
            )

        // then
        with(actualPlan) {
            assertEquals(expectedId, id)
            assertEquals(expectedVersion, version)
            assertEquals(expectedName, name)
            assertEquals(expectedDescription, description)
            assertEquals(expectedIsActive, isActive)
            assertEquals(expectedPrice, price)
            assertEquals(expectedCreatedAt, createdAt)
            assertEquals(expectedUpdatedAt, updatedAt)
            assertEquals(expectedDeletedAt, deletedAt)
        }
    }

    @Test
    fun `given an invalid planId, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'planId' should not be empty"
        val expecetdVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            assertThrows<DomainException> {
                Plan.with(
                    PlanId(""),
                    expecetdVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid name, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'name' should not be empty"

        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = ""
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            assertThrows<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given a name greater than 100 characters, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'name' should not be greater than 100 characters"

        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "a".repeat(101)
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            assertThrows<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid description, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'description' should not be empty"

        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = ""
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            assertThrows<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given a description greater than 500 characters, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'description' should not be greater than 500 characters"

        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "a".repeat(501)
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedDeletedAt = InstantUtils.now()

        // when
        val actualError =
            assertThrows<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    expectedPrice,
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given isActive is not informed, when call with, should return false`() {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = false
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)
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
                deletedAt = expectedDeletedAt
            )

        // then
        assertEquals(expectedIsActive, actualPlan.isActive)
    }

    @Test
    fun `given 0 as price, when call with, should return OK`() {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 0.0)
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
                expectedDeletedAt
            )

        // then
        assertEquals(expectedPrice, actualPlan.price)
    }

    @Test
    fun `given a negative price, when call with, should return error`() {
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
            assertThrows<DomainException> {
                Plan.with(
                    expectedId,
                    expectedVersion,
                    expectedName,
                    expectedDescription,
                    expectedIsActive,
                    MonetaryAmount(Fixture.currency(), -32.90),
                    expectedCreatedAt,
                    expectedUpdatedAt,
                    expectedDeletedAt
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given deletedAt is not informed, when call with, should return null`() {
        // given
        val expectedId = PlanId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedName = "Plus"
        val expectedDescription = "The best plan"
        val expectedIsActive = true
        val expectedPrice = MonetaryAmount(Fixture.currency(), 32.90)
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
                expectedUpdatedAt
            )

        // then
        assertEquals(expectedDeletedAt, actualPlan.deletedAt)
    }
}
