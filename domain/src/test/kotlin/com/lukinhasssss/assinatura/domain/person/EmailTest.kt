package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EmailTest : UnitTest, FunSpec({
    test("given valid Email, when instantiate, should return ValueObject") {
        // given
        val expectedEmail = Fixture.Person.email()

        // when
        val actualEmail = Email(expectedEmail)

        // then
        actualEmail.value shouldBe expectedEmail
    }

    test("given invalid email, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'email' should be a valid email"

        val expectedEmail = "invalidEmail"

        // when
        val actualError =
            shouldThrow<DomainException> {
                Email(expectedEmail)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }
})
