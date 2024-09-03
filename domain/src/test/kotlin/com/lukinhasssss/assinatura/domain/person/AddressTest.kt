package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class AddressTest : UnitTest {
    @Test
    fun `given valid Address, when instantiate, should return ValueObject`() {
        // given
        val expectedZipCode = Fixture.Person.zipCode()
        val expectedNumber = Fixture.Person.buildingNumber()
        val expectedComplement = Fixture.Person.complement()
        val expectedCountry = Fixture.Person.country()

        // when
        val actualAddress = Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)

        // then
        assertEquals(expectedZipCode, actualAddress.zipCode)
        assertEquals(expectedNumber, actualAddress.number)
        assertEquals(expectedComplement, actualAddress.complement)
        assertEquals(expectedCountry, actualAddress.country)
    }

    @Test
    fun `given empty complement, when instantiate, should return ValueObject`() {
        // given
        val expectedZipCode = Fixture.Person.zipCode()
        val expectedNumber = Fixture.Person.buildingNumber()
        val expectedComplement = ""
        val expectedCountry = Fixture.Person.country()

        // when
        val actualAddress =
            assertDoesNotThrow {
                Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)
            }

        // then
        assertEquals(expectedZipCode, actualAddress.zipCode)
        assertEquals(expectedNumber, actualAddress.number)
        assertEquals(expectedComplement, actualAddress.complement)
        assertEquals(expectedCountry, actualAddress.country)
    }

    @Test
    fun `given empty zipCode, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'zipCode' should not be empty"

        val expectedZipCode = ""
        val expectedNumber = Fixture.Person.buildingNumber()
        val expectedComplement = Fixture.Person.complement()
        val expectedCountry = Fixture.Person.country()

        // when
        val actualError =
            assertThrows<DomainException> {
                Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given empty number, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'number' should not be empty"

        val expectedZipCode = Fixture.Person.zipCode()
        val expectedNumber = ""
        val expectedComplement = Fixture.Person.complement()
        val expectedCountry = Fixture.Person.country()

        // when
        val actualError =
            assertThrows<DomainException> {
                Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given empty country, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'country' should not be empty"

        val expectedZipCode = Fixture.Person.zipCode()
        val expectedNumber = Fixture.Person.buildingNumber()
        val expectedComplement = Fixture.Person.complement()
        val expectedCountry = ""

        // when
        val actualError =
            assertThrows<DomainException> {
                Address(expectedZipCode, expectedNumber, expectedComplement, expectedCountry)
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }
}
