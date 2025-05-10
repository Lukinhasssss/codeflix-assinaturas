package com.lukinhasssss.assinatura.domain.plan

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.money.Money
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MonetaryAmountTest : UnitTest, FunSpec({
    test("given valid MonetaryAmount, when instantiate, should return ValueObject") {
        // given
        val expectedCurrency = Fixture.currency()
        val expectedAmount = 100.0

        // when
        val actualMoney = Money(expectedCurrency, expectedAmount)

        // then
        actualMoney.amount shouldBe expectedAmount
        actualMoney.currency.currencyCode shouldBe expectedCurrency
    }

    test("given zero amount, when instantiate, should return ValueObject") {
        // given
        val expectedCurrency = Fixture.currency()
        val expectedAmount = 0.0

        // when
        val actualMoney = Money(expectedCurrency, expectedAmount)

        // then
        actualMoney.amount shouldBe expectedAmount
        actualMoney.currency.currencyCode shouldBe expectedCurrency
    }

    test("given negative amount, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'amount' should be greater than or equal to 0"
        val expectedCurrency = Fixture.currency()
        val expectedAmount = -100.0

        // when
        val actualError =
            shouldThrow<DomainException> {
                Money(expectedCurrency, expectedAmount)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }
})
