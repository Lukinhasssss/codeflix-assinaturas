package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EmailTest : UnitTest {
    @Test
    fun `given valid Email, when instantiate, should return ValueObject`() {
        // given
        val expectedEmail = Fixture.Person.email()

        // when
        val actualEmail = Email(expectedEmail)

        // then
        assertEquals(expectedEmail, actualEmail.value)
    }

    @Test
    fun `given invalid email, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'email' should be a valid email"

        val expectedEmail = "invalidEmail"

        // when
        val actualError =
            assertThrows<DomainException> {
                Email(expectedEmail)
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }
}
