package com.lukinhasssss.assinatura.application.subscription.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.subscription.ChargeSubscription
import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.payment.Payment
import com.lukinhasssss.assinatura.domain.payment.PaymentGateway
import com.lukinhasssss.assinatura.domain.payment.Transaction
import com.lukinhasssss.assinatura.domain.plan.PlanGateway
import com.lukinhasssss.assinatura.domain.subscription.SubscriptionGateway
import com.lukinhasssss.assinatura.domain.subscription.status.SubscriptionStatus
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.Clock
import java.time.LocalDateTime

class DefaultChargeSubscriptionTest : UseCaseTest, FunSpec({
    val accountGateway = mockk<AccountGateway>()
    val clock = mockk<Clock>()
    val paymentGateway = mockk<PaymentGateway>()
    val planGateway = mockk<PlanGateway>()
    val subscriptionGateway = mockk<SubscriptionGateway>()

    val sut =
        DefaultChargeSubscription(
            accountGateway = accountGateway,
            clock = clock,
            paymentGateway = paymentGateway,
            planGateway = planGateway,
            subscriptionGateway = subscriptionGateway,
        )

    test("given a subscription out of charge period, when calls charge subscription, should skip charges") {
        // given
        val referenceDate = LocalDateTime.now().plusDays(2)
        val expectedPlan = Fixture.Plans.plus()
        val expectedAccount = Fixture.Accounts.john()
        val expectedStatus = SubscriptionStatus.ACTIVE
        val expectedSubscription = Fixture.Subscriptions.with(expectedAccount.id, expectedPlan.id, expectedStatus, referenceDate)
        val expectedDueDate = referenceDate.toLocalDate()

        val input =
            object : ChargeSubscription.Input {
                override val accountId = expectedAccount.id.value
                override val subscriptionId = expectedSubscription.id.value
                override val paymentType = Payment.PIX
                override val creditCardToken = null
            }

        every { clock.instant() } returns InstantUtils.now()
        every { subscriptionGateway.subscriptionOfId(any()) } returns expectedSubscription

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.subscriptionId shouldBe expectedSubscription.id
        actualOutput.subscriptionStatus shouldBe expectedStatus
        actualOutput.subscriptionDueDate shouldBe expectedDueDate
        actualOutput.paymentTransaction shouldBe null
    }

    test("given a subscription with past due date, when charge sucessfully, should save as active") {
        // given
        val referenceDate = LocalDateTime.now().minusDays(1)
        val expectedPlan = Fixture.Plans.plus()
        val expectedAccount = Fixture.Accounts.john()
        val expectedStatus = SubscriptionStatus.ACTIVE
        val expectedSubscription = Fixture.Subscriptions.with(expectedAccount.id, expectedPlan.id, SubscriptionStatus.INCOMPLETE, referenceDate)
        val expectedDueDate = referenceDate.toLocalDate().plusMonths(1)
        val expectedTransaction = Transaction.success(IdUtils.uuid())

        val input =
            object : ChargeSubscription.Input {
                override val accountId = expectedAccount.id.value
                override val subscriptionId = expectedSubscription.id.value
                override val paymentType = Payment.PIX
                override val creditCardToken = null
            }

        every { clock.instant() } returns InstantUtils.now()
        every { subscriptionGateway.subscriptionOfId(any()) } returns expectedSubscription
        every { planGateway.planOfId(any()) } returns expectedPlan
        every { accountGateway.accountOfId(any()) } returns expectedAccount
        every { paymentGateway.processPayment(any()) } returns expectedTransaction
        every { subscriptionGateway.save(any()) } answers { firstArg() }

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.subscriptionId shouldBe expectedSubscription.id
        actualOutput.subscriptionStatus shouldBe expectedStatus
        actualOutput.subscriptionDueDate shouldBe expectedDueDate
        actualOutput.paymentTransaction shouldBe expectedTransaction
    }

    test("given a subscription with due date now, when charge sucessfully, should save as active") {
        // given
        val referenceDate = LocalDateTime.now()
        val expectedPlan = Fixture.Plans.plus()
        val expectedAccount = Fixture.Accounts.john()
        val expectedStatus = SubscriptionStatus.ACTIVE
        val expectedSubscription = Fixture.Subscriptions.with(expectedAccount.id, expectedPlan.id, SubscriptionStatus.ACTIVE, referenceDate)
        val expectedDueDate = referenceDate.toLocalDate().plusMonths(1)
        val expectedTransaction = Transaction.success(IdUtils.uuid())

        val input =
            object : ChargeSubscription.Input {
                override val accountId = expectedAccount.id.value
                override val subscriptionId = expectedSubscription.id.value
                override val paymentType = Payment.PIX
                override val creditCardToken = null
            }

        every { clock.instant() } returns InstantUtils.now()
        every { subscriptionGateway.subscriptionOfId(any()) } returns expectedSubscription
        every { planGateway.planOfId(any()) } returns expectedPlan
        every { accountGateway.accountOfId(any()) } returns expectedAccount
        every { paymentGateway.processPayment(any()) } returns expectedTransaction
        every { subscriptionGateway.save(any()) } answers { firstArg() }

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.subscriptionId shouldBe expectedSubscription.id
        actualOutput.subscriptionStatus shouldBe expectedStatus
        actualOutput.subscriptionDueDate shouldBe expectedDueDate
        actualOutput.paymentTransaction shouldBe expectedTransaction
    }

    test("given a subscription with due date now, when charge failure, should save as incomplete") {
        // given
        val referenceDate = LocalDateTime.now()
        val expectedPlan = Fixture.Plans.plus()
        val expectedAccount = Fixture.Accounts.john()
        val expectedStatus = SubscriptionStatus.INCOMPLETE
        val expectedSubscription = Fixture.Subscriptions.with(expectedAccount.id, expectedPlan.id, SubscriptionStatus.ACTIVE, referenceDate)
        val expectedDueDate = referenceDate.toLocalDate()
        val expectedTransaction = Transaction.failure(IdUtils.uuid(), "No funds")

        val input =
            object : ChargeSubscription.Input {
                override val accountId = expectedAccount.id.value
                override val subscriptionId = expectedSubscription.id.value
                override val paymentType = Payment.PIX
                override val creditCardToken = null
            }

        every { clock.instant() } returns InstantUtils.now()
        every { subscriptionGateway.subscriptionOfId(any()) } returns expectedSubscription
        every { planGateway.planOfId(any()) } returns expectedPlan
        every { accountGateway.accountOfId(any()) } returns expectedAccount
        every { paymentGateway.processPayment(any()) } returns expectedTransaction
        every { subscriptionGateway.save(any()) } answers { firstArg() }

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.subscriptionId shouldBe expectedSubscription.id
        actualOutput.subscriptionStatus shouldBe expectedStatus
        actualOutput.subscriptionDueDate shouldBe expectedDueDate
        actualOutput.paymentTransaction shouldBe expectedTransaction
    }

    test("given a subscription with past due date, when charge failure ans past max incomplete days, should save as canceled") {
        // given
        val referenceDate = LocalDateTime.now().minusDays(4)
        val expectedPlan = Fixture.Plans.plus()
        val expectedAccount = Fixture.Accounts.john()
        val expectedStatus = SubscriptionStatus.CANCELED
        val expectedSubscription = Fixture.Subscriptions.with(expectedAccount.id, expectedPlan.id, SubscriptionStatus.ACTIVE, referenceDate)
        val expectedDueDate = referenceDate.toLocalDate()
        val expectedTransaction = Transaction.failure(IdUtils.uuid(), "No funds")

        val input =
            object : ChargeSubscription.Input {
                override val accountId = expectedAccount.id.value
                override val subscriptionId = expectedSubscription.id.value
                override val paymentType = Payment.PIX
                override val creditCardToken = null
            }

        every { clock.instant() } returns InstantUtils.now()
        every { subscriptionGateway.subscriptionOfId(any()) } returns expectedSubscription
        every { planGateway.planOfId(any()) } returns expectedPlan
        every { accountGateway.accountOfId(any()) } returns expectedAccount
        every { paymentGateway.processPayment(any()) } returns expectedTransaction
        every { subscriptionGateway.save(any()) } answers { firstArg() }

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.subscriptionId shouldBe expectedSubscription.id
        actualOutput.subscriptionStatus shouldBe expectedStatus
        actualOutput.subscriptionDueDate shouldBe expectedDueDate
        actualOutput.paymentTransaction shouldBe expectedTransaction
    }
})
