package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.account.AccountEvent.AccountCreated
import com.lukinhasssss.assinatura.domain.account.idp.UserId
import com.lukinhasssss.assinatura.domain.exception.DomainException
import com.lukinhasssss.assinatura.domain.person.Email
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import com.lukinhasssss.assinatura.domain.utils.InstantUtils
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class AccountCreatedTest : UnitTest, FunSpec({
    test("given valid params, when instantiate event, should return it") {
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
            this shouldNotBe null
            aggregateId shouldBe expectedAggregateId
            aggregateType shouldBe expectedAggregateType
            accountId shouldBe expectedAccountId.value
            email shouldBe expectedEmail.value
            fullName shouldBe expectedName.fullName()
            occurredOn shouldNotBe null
        }
    }

    test("given empty account id, when instantiate, should return error") {
        // given
        val expectedErrorMessage = "'accountId' should not be empty"

        val expectedAccountId = ""
        val expectedFullName = Fixture.Person.fullName().fullName()
        val expectedEmail = Fixture.Person.email()
        val expectedOccurredOn = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                AccountCreated(expectedAccountId, expectedEmail, expectedFullName, expectedOccurredOn)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given empty email, when instantiate, should return error") {
        // given
        val expectedErrorMessage = "'email' should not be empty"

        val expectedAccountId = AccountId(IdUtils.uuid()).value
        val expectedEmail = ""
        val expectedFullName = Fixture.Person.fullName().fullName()
        val expectedOccurredOn = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                AccountCreated(expectedAccountId, expectedEmail, expectedFullName, expectedOccurredOn)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }

    test("given empty full name, when instantiate, should return error") {
        // given
        val expectedErrorMessage = "'fullName' should not be empty"

        val expectedAccountId = AccountId(IdUtils.uuid()).value
        val expectedEmail = Fixture.Person.email()
        val expectedFullName = ""
        val expectedOccurredOn = InstantUtils.now()

        // when
        val actualError =
            shouldThrow<DomainException> {
                AccountCreated(expectedAccountId, expectedEmail, expectedFullName, expectedOccurredOn)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }
})
