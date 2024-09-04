package com.lukinhasssss.assinatura.domain.person

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NameTest : UnitTest {
    @Test
    fun `given valid Name, when instantiate, should return ValueObject`() {
        // given
        val expectedFirstName = Fixture.Person.firstName()
        val expectedLastName = Fixture.Person.lastName()

        // when
        val actualName = Name(expectedFirstName, expectedLastName)

        // then
        assertEquals(expectedFirstName, actualName.firstName)
        assertEquals(expectedLastName, actualName.lastName)
    }

    @Test
    fun `given empty firstName, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'firstName' should not be empty"

        val expectedFirstName = ""
        val expectedLastName = Fixture.Person.lastName()

        // when
        val actualError =
            assertThrows<DomainException> {
                Name(expectedFirstName, expectedLastName)
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given empty lastName, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'lastName' should not be empty"

        val expectedFirstName = Fixture.Person.firstName()
        val expectedLastName = ""

        // when
        val expectedError =
            assertThrows<DomainException> {
                Name(expectedFirstName, expectedLastName)
            }

        // then
        assertEquals(expectedErrorMessage, expectedError.message)
    }
}
