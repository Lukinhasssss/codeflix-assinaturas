package com.lukinhasssss.assinatura.application.account.impl

import com.lukinhasssss.assinatura.application.UseCaseTest
import com.lukinhasssss.assinatura.application.account.CreateIdpUser
import com.lukinhasssss.assinatura.domain.Fixture
import com.lukinhasssss.assinatura.domain.account.idp.IdentityProviderGateway
import com.lukinhasssss.assinatura.domain.account.idp.UserId
import com.lukinhasssss.assinatura.domain.utils.IdUtils
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class DefaultCreateIdpUserTest : UseCaseTest, FunSpec({
    val identityProviderGateway = mockk<IdentityProviderGateway>()

    val sut = DefaultCreateIdpUser(identityProviderGateway)

    test("given valid input, when calls execute, should return user id") {
        // given
        val expectedFirstName = Fixture.Person.firstName()
        val expectedLastName = Fixture.Person.lastName()
        val expectedEmail = Fixture.Person.email()
        val expectedPassword = IdUtils.uuid()
        val expectedUserId = UserId.from(IdUtils.uuid())

        val input =
            object : CreateIdpUser.Input {
                override val firstName = expectedFirstName
                override val lastName = expectedLastName
                override val email = expectedEmail
                override val password = expectedPassword
            }

        every { identityProviderGateway.create(any()) } returns expectedUserId

        // when
        val actualOutput = sut.execute(input)

        // then
        actualOutput.idpUserId shouldBe expectedUserId
    }
})
