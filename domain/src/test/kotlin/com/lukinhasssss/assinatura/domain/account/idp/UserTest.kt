package com.lukinhasssss.assinatura.domain.account.idp

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.person.Email
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class UserTest : UnitTest, FunSpec({
    test("given valid params, when instantiate user using method new, should return it") {
        // given
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email(Fixture.Person.email())
        val expectedPassword = "password"

        // when
        val actualUser = User.new(expectedName, expectedEmail, expectedPassword)

        // then
        with(actualUser) {
            this shouldNotBe null
            name shouldBe expectedName
            email shouldBe expectedEmail
            password shouldBe expectedPassword
            enabled shouldBe true
            emailVerified shouldBe false
        }
    }

    test("given empty password, when instantiate user using method new, should throw DomainException") {
        // given
        val expectedErrorMessage = "User 'password' cannot not be empty for new users"

        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email(Fixture.Person.email())
        val expectedPassword = ""

        // when
        val actualError =
            shouldThrow<DomainException> {
                User.new(expectedName, expectedEmail, expectedPassword)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given valid params, when instantiate user using method with, should return it") {
        // given
        val expectedUserId = UserId.from("USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email(Fixture.Person.email())
        val expectedEmailVerified = true
        val expectedEnabled = false

        // when
        val actualUser =
            User.with(
                userId = expectedUserId,
                name = expectedName,
                email = expectedEmail,
                emailVerified = expectedEmailVerified,
                enabled = expectedEnabled,
            )

        // then
        with(actualUser) {
            this shouldNotBe null
            userId shouldBe expectedUserId
            name shouldBe expectedName
            email shouldBe expectedEmail
            emailVerified shouldBe expectedEmailVerified
            enabled shouldBe expectedEnabled
        }
    }
})
