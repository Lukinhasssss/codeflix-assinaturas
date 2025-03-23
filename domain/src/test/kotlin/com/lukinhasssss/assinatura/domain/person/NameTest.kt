package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NameTest : UnitTest, FunSpec({
    test("given valid Name, when instantiate, should return ValueObject") {
        // given
        val expectedFirstName = Fixture.Person.firstName()
        val expectedLastName = Fixture.Person.lastName()

        // when
        val actualName = Name(expectedFirstName, expectedLastName)

        // then
        actualName.firstName shouldBe expectedFirstName
        actualName.lastName shouldBe expectedLastName
    }

    test("given empty firstName, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'firstName' should not be empty"

        val expectedFirstName = ""
        val expectedLastName = Fixture.Person.lastName()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Name(expectedFirstName, expectedLastName)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given empty lastName, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'lastName' should not be empty"

        val expectedFirstName = Fixture.Person.firstName()
        val expectedLastName = ""

        // when
        val actualError =
            shouldThrow<DomainException> {
                Name(expectedFirstName, expectedLastName)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }
})
