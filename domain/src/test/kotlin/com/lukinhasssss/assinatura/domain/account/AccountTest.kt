package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.person.Address
import com.lukinhasssss.assinatura.domain.person.Document
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.person.Name
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class AccountTest : UnitTest {
    @Test
    fun `Given a new account, when calls new account, should instantiate`() {
        // given
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 0
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")

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
        }
    }

    @Test
    fun `Given valid params, when calls with, should instantiate`() {
        // given
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress = Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

        // when - then
        assertDoesNotThrow {
            Account.with(expectedId, expectedVersion, expectedUserId, expectedName, expectedEmail, expectedDocument, expectedAddress)
        }
    }

    @Test
    fun `Given null address, when calls with, should instantiate`() {
        // given
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
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
    fun `Given an invalid accountId, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'accountId' should not be empty"
        val expectedId = ""
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an invalid userId, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'userId' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = ""
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an invalid firstName, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'firstName' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an invalid lastName, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'lastName' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedEmail = Email("john@gmail.com")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an invalid email, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'email' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedDocument = Document.create(documentType = "cpf", documentNumber = "12345678912")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an invalid document type, when call with, should return error`() {
        // given
        val expectedErrorMessage = "Invalid document type"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an empty cpf document number, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'cpf' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an invalid cpf document number, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'cpf' is invalid"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an empty cnpj document number, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'cnpj' should not be empty"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
    fun `Given an invalid cnpj document number, when call with, should return error`() {
        // given
        val expectedErrorMessage = "'cnpj' is invalid"
        val expectedId = AccountId(IdUtils.uuid())
        val expectedVersion = 1
        val expectedUserId = UserId(" USER-123")
        val expectedName = Name(firstName = "John", lastName = "Doe")
        val expectedEmail = Email("john@gmail.com")
        val expectedAddress =
            Address(zipCode = "09123123", number = "123", complement = "ap 123", country = "Brazil")

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
