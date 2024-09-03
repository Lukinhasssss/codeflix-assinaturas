package com.lukinhasssss.assinatura.domain.plan

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Currency

class MonetaryAmountTest : UnitTest {
    @Test
    fun `given valid MonetaryAmount, when instantiate, should return ValueObject`() {
        // given
        val expectedCurrency = Fixture.currency()
        val expectedAmount = 100.0

        // when
        val actualMonetaryAmount = MonetaryAmount(expectedCurrency, expectedAmount)

        // then
        assertEquals(expectedAmount, actualMonetaryAmount.amount)
        assertEquals(expectedCurrency, actualMonetaryAmount.currency.currencyCode)
    }

    @Test
    fun `given zero amount, when instantiate, should return ValueObject`() {
        // given
        val expectedCurrency = Fixture.currency()
        val expectedAmount = 0.0

        // when
        val actualMonetaryAmount = MonetaryAmount(expectedCurrency, expectedAmount)

        // then
        assertEquals(expectedAmount, actualMonetaryAmount.amount)
        assertEquals(expectedCurrency, actualMonetaryAmount.currency.currencyCode)
    }

    @Test
    fun `given negative amount, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'amount' should be greater than or equal to 0"

        val expectedCurrency = Fixture.currency()
        val expectedAmount = -100.0

        // when
        val exception = assertThrows<DomainException> {
            MonetaryAmount(expectedCurrency, expectedAmount)
        }

        // then
        assertEquals(expectedErrorMessage, exception.message)
    }
}