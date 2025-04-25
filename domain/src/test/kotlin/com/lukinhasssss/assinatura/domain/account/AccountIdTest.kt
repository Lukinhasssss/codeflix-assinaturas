package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AccountIdTest : UnitTest, FunSpec({
    test("given valid id, when instantiate, should return ValueObject") {
        // given
        val expectedAccountId = "12345678-1234-1234-1234-123456789012"

        // when
        val actualAccountId = AccountId(expectedAccountId)

        // then
        actualAccountId.value shouldBe expectedAccountId
    }

    test("given empty id, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'accountId' should not be empty"

        val expectedAccountId = ""

        // when
        val actualError =
            shouldThrow<DomainException> {
                AccountId(expectedAccountId)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }
})
