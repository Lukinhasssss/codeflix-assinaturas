package com.lukinhasssss.assinatura.domain.account

import com.lukinhasssss.assinatura.domain.UnitTest
import com.lukinhasssss.assinatura.domain.exception.DomainException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AccountIdTest : UnitTest {
    @Test
    fun `given valid id, when instantiate, should return ValueObject`() {
        // given
        val expectedAccountId = "12345678-1234-1234-1234-123456789012"

        // when
        val actualAccountId = AccountId(expectedAccountId)

        // then
        assertEquals(expectedAccountId, actualAccountId.value)
    }

    @Test
    fun `given empty id, when instantiate, should throws DomainException`() {
        // given
        val expectedErrorMessage = "'accountId' should not be empty"

        val expectedAccountId = ""

        // when
        val actualError =
            assertThrows<DomainException> {
                AccountId(expectedAccountId)
            }

        // then
        assertEquals(expectedErrorMessage, actualError.message)
    }
}
