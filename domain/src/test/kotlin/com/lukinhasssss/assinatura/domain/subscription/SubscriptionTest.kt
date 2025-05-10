package com.lukinhasssss.assinatura.domain.subscription

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.plan.PlanId
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class SubscriptionTest : UnitTest, FunSpec({
    test("given valid params, when calls new subscription, should instantiate") {
        // given
        val expectedId = SubscriptionId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedAccountId = AccountId(IdUtils.uuid())
        val expectedPlan = Fixture.Plans.plus()
        val expectedStatus = SubscriptionStatus.TRIALING
        val expectedDueDate = LocalDate.now().plusMonths(1)
        val expectedLastRenewDate: Instant? = null
        val expectedLastTransactionId: String? = null
        val expectedEventsCount = 1

        // when
        val actualSubscription = Subscription.new(expectedId, expectedAccountId, expectedPlan)

        // then
        with(actualSubscription) {
            this.shouldNotBeNull()
            id shouldBe expectedId
            version shouldBe expectedVersion
            accountId shouldBe expectedAccountId
            planId shouldBe expectedPlan.id
            status.value() shouldBe expectedStatus
            dueDate shouldBe expectedDueDate
            lastRenewDate shouldBe expectedLastRenewDate
            lastTransactionId shouldBe expectedLastTransactionId
            createdAt.shouldNotBeNull()
            updatedAt.shouldNotBeNull()
            domainEvents.size shouldBe expectedEventsCount
            domainEvents.first()::class shouldBeSameInstanceAs SubscriptionEvent.SubscriptionCreated::class
        }
    }

    test("given valid params, when calls with, should instantiate") {
        // given
        val expectedId = SubscriptionId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedAccountId = AccountId(IdUtils.uuid())
        val expectedPlanId = PlanId(IdUtils.uuid())
        val expectedStatus = SubscriptionStatus.TRIALING
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
            this.shouldNotBeNull()
            id shouldBe expectedId
            version shouldBe expectedVersion
            accountId shouldBe expectedAccountId
            planId shouldBe expectedPlanId
            status.value() shouldBe expectedStatus
            dueDate shouldBe expectedDueDate
            lastRenewDate shouldBe expectedLastRenewDate
            lastTransactionId shouldBe expectedLastTransactionId
            createdAt shouldBe expectedCreatedAt
            updatedAt shouldBe expectedUpdatedAt
            domainEvents.isEmpty().shouldBeTrue()
        }
    }

    test("given trialing subscription, when execute IncompleteCommand, should transit to incomplete state") {
        // given
        val expectedId = SubscriptionId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedAccountId = AccountId(IdUtils.uuid())
        val expectedPlanId = PlanId(IdUtils.uuid())
        val expectedStatus = SubscriptionStatus.INCOMPLETE
        val expectedDueDate = LocalDate.now()
        val expectedLastRenewDate: Instant? = null
        val expectedLastTransactionId = IdUtils.uuid()
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedReason = "Fail to charge credit card"
        val expectedEventsCount = 1

        val actualSubscription =
            Subscription.with(
                subscriptionId = expectedId,
                version = expectedVersion,
                accountId = expectedAccountId,
                planId = expectedPlanId,
                status = SubscriptionStatus.TRIALING,
                dueDate = expectedDueDate,
                lastRenewDate = expectedLastRenewDate,
                createdAt = expectedCreatedAt,
                updatedAt = expectedUpdatedAt,
            )

        Thread.sleep(10)

        // when
        actualSubscription.execute(SubscriptionCommand.IncompleteSubscription(expectedReason, expectedLastTransactionId))

        // then
        with(actualSubscription) {
            this.shouldNotBeNull()
            id shouldBe expectedId
            version shouldBe expectedVersion
            accountId shouldBe expectedAccountId
            planId shouldBe expectedPlanId
            status.value() shouldBe expectedStatus
            dueDate shouldBe expectedDueDate
            lastRenewDate shouldBe expectedLastRenewDate
            lastTransactionId shouldBe expectedLastTransactionId
            createdAt shouldBe expectedCreatedAt
            updatedAt shouldBeAfter expectedUpdatedAt
            domainEvents.size shouldBe expectedEventsCount
            domainEvents.first()::class shouldBeSameInstanceAs SubscriptionEvent.SubscriptionIncomplete::class
        }
    }

    test("given trialing subscription, when execute RenewCommand, should transit to active state") {
        // given
        val expectedPlan = Fixture.Plans.plus()
        val expectedId = SubscriptionId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedAccountId = AccountId(IdUtils.uuid())
        val expectedPlanId = expectedPlan.id
        val expectedStatus = SubscriptionStatus.ACTIVE
        val expectedDueDate = LocalDate.now().plusMonths(1)
        val expectedLastTransactionId = IdUtils.uuid()
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedEventsCount = 1

        val actualSubscription =
            Subscription.with(
                subscriptionId = expectedId,
                version = expectedVersion,
                accountId = expectedAccountId,
                planId = expectedPlanId,
                status = SubscriptionStatus.TRIALING,
                dueDate = LocalDate.now(),
                createdAt = expectedCreatedAt,
                updatedAt = expectedUpdatedAt,
            )

        Thread.sleep(10)

        // when
        actualSubscription.execute(SubscriptionCommand.RenewSubscription(expectedPlan, expectedLastTransactionId))

        // then
        with(actualSubscription) {
            this.shouldNotBeNull()
            id shouldBe expectedId
            version shouldBe expectedVersion
            accountId shouldBe expectedAccountId
            planId shouldBe expectedPlanId
            status.value() shouldBe expectedStatus
            dueDate shouldBe expectedDueDate
            lastRenewDate.shouldNotBeNull()
            lastTransactionId shouldBe expectedLastTransactionId
            createdAt shouldBe expectedCreatedAt
            updatedAt shouldBeAfter expectedUpdatedAt
            domainEvents.size shouldBe expectedEventsCount
            domainEvents.first()::class shouldBeSameInstanceAs SubscriptionEvent.SubscriptionRenewed::class
        }
    }

    test("given trialing subscription, when execute CancelCommand, should transit to canceled state") {
        // given
        val expectedId = SubscriptionId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedAccountId = AccountId(IdUtils.uuid())
        val expectedPlanId = PlanId(IdUtils.uuid())
        val expectedStatus = SubscriptionStatus.CANCELED
        val expectedDueDate = LocalDate.now().plusMonths(1)
        val expectedLastRenewDate: Instant = InstantUtils.now()
        val expectedLastTransactionId = IdUtils.uuid()
        val expectedCreatedAt = InstantUtils.now()
        val expectedUpdatedAt = InstantUtils.now()
        val expectedEventsCount = 1

        val actualSubscription =
            Subscription.with(
                subscriptionId = expectedId,
                version = expectedVersion,
                accountId = expectedAccountId,
                planId = expectedPlanId,
                status = SubscriptionStatus.TRIALING,
                dueDate = expectedDueDate,
                lastRenewDate = expectedLastRenewDate,
                lastTransactionId = expectedLastTransactionId,
                createdAt = expectedCreatedAt,
                updatedAt = expectedUpdatedAt,
            )

        Thread.sleep(10)

        // when
        actualSubscription.execute(SubscriptionCommand.CancelSubscription())

        // then
        with(actualSubscription) {
            this.shouldNotBeNull()
            id shouldBe expectedId
            version shouldBe expectedVersion
            accountId shouldBe expectedAccountId
            planId shouldBe expectedPlanId
            status.value() shouldBe expectedStatus
            dueDate shouldBe expectedDueDate
            lastRenewDate.shouldNotBeNull()
            lastTransactionId shouldBe expectedLastTransactionId
            createdAt shouldBe expectedCreatedAt
            updatedAt shouldBeAfter expectedUpdatedAt
            domainEvents.size shouldBe expectedEventsCount
            domainEvents.first()::class shouldBeSameInstanceAs SubscriptionEvent.SubscriptionCanceled::class
        }
    }
})
