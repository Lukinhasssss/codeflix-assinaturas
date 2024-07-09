package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.person.Document
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.person.Name
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class AccountTest : UnitTest {
    @Test
    fun `given valid params, when calls new account, should instantiate and dispatch event`() {
        // given
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedEventsCount = 1
        val expecetedAggregatedId = expectedId.value
        val expectedAggregatedType = "Account"

        // when
        val actualAccount =
            Account.newAccount(
                anAccountId = expectedId,
                anUserId = expectedUserId,
                aName = expectedName,
                anEmail = expectedEmail,
                aDocument = expectedDocument,
            )

        // then
        with(actualAccount) {
            assertEquals(expectedId, id)
            assertEquals(expectedVersion, version)
            assertEquals(expectedUserId, userId)
            assertEquals(expectedName, name)
            assertEquals(expectedEmail, email)
            assertEquals(expectedDocument, document)
            assertEquals(expectedEventsCount, domainEvents.size)
            assertInstanceOf(AccountEvent.AccountCreated::class.java, domainEvents.first())
        }
    }

    @Test
    fun `given valid params, when calls with, should instantiate`() {
        // given
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress = Fixture.Person.fullAddress()

        // when - then
        assertDoesNotThrow {
            Account.with(expectedId, expectedVersion, expectedUserId, expectedName, expectedEmail, expectedDocument, expectedAddress)
        }
    }

    @Test
    fun `given null address, when calls with, should instantiate`() {
        // given
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")

        // when
        val actualAccount =
            Account.with(
                anAccountId = expectedId,
                version = expectedVersion,
                anUserId = expectedUserId,
                aName = expectedName,
                anEmail = expectedEmail,
                aDocument = Document.create(documentType = "cpf", documentNumber = "12345678912"),
            )

        // then
        with(actualAccount) {
            assertEquals(expectedId, id)
            assertEquals(expectedVersion, version)
            assertEquals(expectedUserId, userId)
            assertEquals(expectedName, name)
            assertEquals(expectedEmail, email)
        }
    }

    @Test
    fun `given an invalid accountId, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'accountId' should not be empty"
        val expectedId = ""
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = AccountId(expectedId),
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = expectedName,
                    anEmail = expectedEmail,
                    aDocument = expectedDocument,
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid userId, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'userId' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = ""
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = UserId(expectedUserId),
                    aName = expectedName,
                    anEmail = expectedEmail,
                    aDocument = expectedDocument,
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid firstName, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'firstName' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = Name(firstName = "", lastName = "lastName"),
                    anEmail = expectedEmail,
                    aDocument = expectedDocument,
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid lastName, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'lastName' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = Name(firstName = "John", lastName = ""),
                    anEmail = expectedEmail,
                    aDocument = expectedDocument,
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid email, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'email' should be a valid email"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = expectedName,
                    anEmail = Email(""),
                    aDocument = expectedDocument,
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid document type, when call with, should return error`() {
        // given
        val expectedErrorMessage = "Invalid document type"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = expectedName,
                    anEmail = expectedEmail,
                    aDocument = Document.create(documentType = "rg", documentNumber = ""),
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an empty cpf document number, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'cpf' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = expectedName,
                    anEmail = expectedEmail,
                    aDocument = Document.create(documentType = "cpf", documentNumber = ""),
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid cpf document number, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'cpf' is invalid"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = expectedName,
                    anEmail = expectedEmail,
                    aDocument = Document.create(documentType = "cpf", documentNumber = "12345678912345"),
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an empty cnpj document number, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'cnpj' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = expectedName,
                    anEmail = expectedEmail,
                    aDocument = Document.create(documentType = "cnpj", documentNumber = ""),
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given an invalid cnpj document number, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'cnpj' is invalid"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress = Fixture.Person.fullAddress()

        // when
        val actualError =
            assertThrows<DomainException> {
                Account.with(
                    anAccountId = expectedId,
                    version = expectedVersion,
                    anUserId = expectedUserId,
                    aName = expectedName,
                    anEmail = expectedEmail,
                    aDocument = Document.create(documentType = "cnpj", documentNumber = "12345678912"),
                    billingAddress = expectedAddress,
                )
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }
}
