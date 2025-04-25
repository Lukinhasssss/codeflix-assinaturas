package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AddressTest : UnitTest, FunSpec({
    test("given valid Address, when instantiate, should return ValueObject") {
        // given
        val expectedZipCode = Fixture.Person.zipCode()
        val expectedNumber = Fixture.Person.buildingNumber()
        val expectedComplement = Fixture.Person.complement()
        val expectedCountry = Fixture.Person.country()

        // when
        val actualAddress = Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)

        // then
        actualAddress.zipCode shouldBe expectedZipCode
        actualAddress.number shouldBe expectedNumber
        actualAddress.complement shouldBe expectedComplement
        actualAddress.country shouldBe expectedCountry
    }

    test("given empty complement, when instantiate, should return ValueObject") {
        // given
        val expectedZipCode = Fixture.Person.zipCode()
        val expectedNumber = Fixture.Person.buildingNumber()
        val expectedComplement = ""
        val expectedCountry = Fixture.Person.country()

        // when
        val actualAddress = Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)

        // then
        actualAddress.zipCode shouldBe expectedZipCode
        actualAddress.number shouldBe expectedNumber
        actualAddress.complement shouldBe expectedComplement
        actualAddress.country shouldBe expectedCountry
    }

    test("given empty zipCode, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'zipCode' should not be empty"

        val expectedZipCode = ""
        val expectedNumber = Fixture.Person.buildingNumber()
        val expectedComplement = Fixture.Person.complement()
        val expectedCountry = Fixture.Person.country()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given empty number, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'number' should not be empty"

        val expectedZipCode = Fixture.Person.zipCode()
        val expectedNumber = ""
        val expectedComplement = Fixture.Person.complement()
        val expectedCountry = Fixture.Person.country()

        // when
        val actualError =
            shouldThrow<DomainException> {
                Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given empty country, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'country' should not be empty"

        val expectedZipCode = Fixture.Person.zipCode()
        val expectedNumber = Fixture.Person.buildingNumber()
        val expectedComplement = Fixture.Person.complement()
        val expectedCountry = ""

        // when
        val actualError =
            shouldThrow<DomainException> {
                Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }
})
