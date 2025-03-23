package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.plan.PlanId
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class SubscriptionTest : UnitTest {
    @Test
    fun `given valid params, when calls new subscription, should instantiate`() {
        // given
        val expectedId = SubscriptionId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedAccountId = AccountId(IdUtils.uuid())
        val expectedPlan = Fixture.Plans.plus()
        val expectedStatus = SubscriptionStatus.TRAILING
        val expectedDueDate = LocalDate.now().plusMonths(1)
        val expectedLastRenewDate: Instant? = null
        val expectedLastTransactionId: String? = null
        // val expectedEventsCount = 1

        // when
        val actualSubscription = Subscription.new(expectedId, expectedAccountId, expectedPlan)

        // then
        with(actualSubscription) {
            assertNotNull(this)
            assertEquals(expectedId, id)
            assertEquals(expectedVersion, version)
            assertEquals(expectedAccountId, accountId)
            assertEquals(expectedPlan.id, planId)
            assertEquals(expectedStatus, status.value())
            assertEquals(expectedDueDate, dueDate)
            assertEquals(expectedLastRenewDate, lastRenewDate)
            assertEquals(expectedLastTransactionId, lastTransactionId)
            assertNotNull(createdAt)
            assertNotNull(updatedAt)
        }
    }

    @Test
    fun `given valid params, when calls with, should instantiate`() {
        // given
        val expectedId = SubscriptionId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedAccountId = AccountId(IdUtils.uuid())
        val expectedPlanId = PlanId(IdUtils.uuid())
        val expectedStatus = SubscriptionStatus.TRAILING
        val expectedDueDate = LocalDate.now().plusMonths(1)
        val expectedLastRenewDate = InstantUtils.now().minus(7, ChronoUnit.DAYS)
        val expectedLastTransactionId = IdUtils.uuid()
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()

        // when
        val actualSubscription =
            Subscription.with(
                subscriptionId = expectedId,
                version = expectedVersion,
                accountId = expectedAccountId,
                planId = expectedPlanId,
                status = expectedStatus,
                dueDate = expectedDueDate,
                lastRenewDate = expectedLastRenewDate,
                lastTransactionId = expectedLastTransactionId,
                createdAt = expectedCreatedAt,
                updatedAt = expectedUpdatedAt,
            )

        // then
        with(actualSubscription) {
            assertNotNull(this)
            assertEquals(expectedId, id)
            assertEquals(expectedVersion, version)
            assertEquals(expectedAccountId, accountId)
            assertEquals(expectedPlanId, planId)
            assertEquals(expectedStatus, status.value())
            assertEquals(expectedDueDate, dueDate)
            assertEquals(expectedLastRenewDate, lastRenewDate)
            assertEquals(expectedLastTransactionId, lastTransactionId)
            assertEquals(expectedCreatedAt, createdAt)
            assertEquals(expectedUpdatedAt, updatedAt)
            assertTrue(domainEvents.isEmpty())
        }
    }
}
