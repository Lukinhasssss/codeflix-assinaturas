package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.account.AccountEvent.AccountCreated
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AccountCreatedTest : UnitTest {
    @Test
    fun `given valid params, when instantiate event, should return it`() {
        // given
        val expectedAccountId = AccountId(IdUtils.uuid())
        val expectedUserId = UserId("USER-123")
        val expectedName = Fixture.Person.fullName()
        val expectedEmail = Email(Fixture.Person.email())
        val expectedDocument = Fixture.Person.document()
        val expectedAggregateId = expectedAccountId.value
        val expectedAggregateType = Account::class.simpleName

        val actualAccount = Account.newAccount(expectedAccountId, expectedUserId, expectedName, expectedEmail, expectedDocument)

        // when
        val actualEvent = AccountCreated(actualAccount)

        // then
        with(actualEvent) {
            assertNotNull(this)
            assertEquals(expectedAggregateId, aggregateId)
            assertEquals(expectedAggregateType, aggregateType)
            assertEquals(expectedAccountId.value, accountId)
            assertEquals(expectedEmail.value, email)
            assertEquals(expectedName.fullName(), fullName)
            assertNotNull(occurredOn)
        }
    }

    @Test
    fun `given empty account id, when instantiate, should return error`() {
        // given
        val expectedErrorMessage = "'accountId' should not be empty"

        val expectedAccountId = ""
        val expectedFullName = Fixture.Person.fullName().fullName()
        val expectedEmail = Fixture.Person.email()
        val expectedOccurredOn = InstantUtils.now()

        // when
        val actualError = assertThrows<DomainException> {
            AccountCreated(expectedAccountId, expectedEmail, expectedFullName, expectedOccurredOn)
        }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given empty email, when instantiate, should return error`() {
        // given
        val expectedErrorMessage = "'email' should not be empty"

        val expectedAccountId = AccountId(IdUtils.uuid()).value
        val expectedEmail = ""
        val expectedFullName = Fixture.Person.fullName().fullName()
        val expectedOccurredOn = InstantUtils.now()

        // when
        val actualError = assertThrows<DomainException> {
            AccountCreated(expectedAccountId, expectedEmail, expectedFullName, expectedOccurredOn)
        }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }

    @Test
    fun `given empty full name, when instantiate, should return error`() {
        // given
        val expectedErrorMessage = "'fullName' should not be empty"

        val expectedAccountId = AccountId(IdUtils.uuid()).value
        val expectedEmail = Fixture.Person.email()
        val expectedFullName = ""
        val expectedOccurredOn = InstantUtils.now()

        // when
        val actualError = assertThrows<DomainException> {
            AccountCreated(expectedAccountId, expectedEmail, expectedFullName, expectedOccurredOn)
        }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }
}