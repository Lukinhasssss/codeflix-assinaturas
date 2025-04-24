package com.lukinhasssss.assinatura.domain.account.idp

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UserIdTest : UnitTest, FunSpec({
    test("given valid id, when instantiate, should return ValueObject") {
        // given
        val expectedUserId = "12345678-1234-1234-1234-123456789012"

        // when
        val actualUserId = UserId(expectedUserId)

        // then
        actualUserId.value shouldBe expectedUserId
    }

    test("given empty id, when instantiate, should throws DomainException") {
        // given
        val expectedErrorMessage = "'userId' should not be empty"

        val expectedUserId = ""

        // when
        val actualError =
            shouldThrow<DomainException> {
                UserId(expectedUserId)
            }

        // then
        actualError.message shouldBe expectedErrorMessage
    }
})
