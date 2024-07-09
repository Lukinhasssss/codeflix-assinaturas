package com.lukinhasssss.assinatura.domain.account.iam

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.account.UserId
import com.lukinhasssss.assinatura.domain.exception.DomainException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserIdTest : UnitTest {
    @Test
    fun `given valid id, when instantiate, should return ValueObject`() {
        // given
        val expectedUserId = "12345678-1234-1234-1234-123456789012"

        // when
        val actualUserId = UserId(expectedUserId)

        // then
        assertEquals(expectedUserId, actualUserId.value)
    }

    @Test
    fun `given empty id, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'userId' should not be empty"

        val expectedUserId = ""

        // when
        val actualError =
            assertThrows<DomainException> {
                UserId(expectedUserId)
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }
}
