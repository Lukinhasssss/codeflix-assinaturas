package com.lukinhasssss.assinatura.application.account.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.account.CreateAccount
import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.account.AccountGateway
import com.lukinhasssss.assinatura.domain.account.AccountId
import com.lukinhasssss.assinatura.domain.account.idp.UserId
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class DefaultCreateAccountTest : UseCaseTest, FunSpec({
    val accountGateway = mockk<AccountGateway>()

    val sut = DefaultCreateAccount(accountGateway)

    test("given valid input, when calls execute, should return account id") {
        // given
        val expectedFirstName = Fixture.Person.firstName()
        val expectedLastName = Fixture.Person.lastName()
        val expectedEmail = Fixture.Person.email()
        val expectedDocument = Fixture.Person.document()
        val expectedUserId = UserId.from(IdUtils.uuid())
        val expectedAccountId = AccountId(IdUtils.uuid())

        val input =
            object : CreateAccount.Input {
                override val userId = expectedUserId.value
                override val firstName = expectedFirstName
                override val lastName = expectedLastName
                override val email = expectedEmail
                override val documentType = expectedDocument.type
                override val documentNumber = expectedDocument.value
            }

        every { accountGateway.nextId() } returns expectedAccountId
        every { accountGateway.save(any()) } answers { firstArg() }

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.accountId shouldBe expectedAccountId
    }
})
