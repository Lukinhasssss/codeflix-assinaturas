package com.lukinhasssss.assinatura.infrastructure.authentication

import com.lukinhasssss.assinatura.domain.exception.InternalErrorException
import com.lukinhasssss.assinatura.infrastructure.authentication.AuthenticationGateway.AuthenticationResult
import com.lukinhasssss.assinatura.infrastructure.authentication.AuthenticationGateway.ClientCredentialsInput
import com.lukinhasssss.assinatura.infrastructure.authentication.AuthenticationGateway.RefreshTokenInput
import com.lukinhasssss.assinatura.infrastructure.authentication.ClientCredentialsManager.ClientCredentials
import com.lukinhasssss.assinatura.infrastructure.configuration.properties.KeycloakProperties
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.util.ReflectionTestUtils

@ExtendWith(MockKExtension::class)
class ClientCredentialsManagerTest {
    private val keycloakProperties = mockk<KeycloakProperties>()

    private val authenticationGateway = mockk<AuthenticationGateway>()

    @InjectMockKs
    private lateinit var manager: ClientCredentialsManager

    @Test
    fun givenAValidAuthenticationResult_whenCallsRefresh_shouldCreateCredentials() {
        // given
        val expectedAccessToken = "accessToken"
        val expectedRefreshToken = "refreshToken"
        val expectedClientId = "client-id"
        val expectedClientSecret = "client-secret"

        every { keycloakProperties.clientId } returns expectedClientId
        every { keycloakProperties.clientSecret } returns expectedClientSecret
        every {
            authenticationGateway.login(ClientCredentialsInput(expectedClientId, expectedClientSecret))
        } returns AuthenticationResult(expectedAccessToken, expectedRefreshToken)

        // when
        manager.refresh()
        val actualToken = manager.retrieve()

        // then
        assertEquals(expectedAccessToken, actualToken)
    }

    @Test
    fun givenPreviousAuthentication_whenCallsRefresh_shouldUpdateCredentials() {
        // given
        val expectedAccessToken = "accessToken"
        val expectedRefreshToken = "refreshToken"
        val expectedClientId = "client-id"
        val expectedClientSecret = "client-secret"

        ReflectionTestUtils.setField(manager, "credentials", ClientCredentials(expectedClientId, "acc", "ref"))

        every { keycloakProperties.clientId } returns expectedClientId
        every { keycloakProperties.clientSecret } returns expectedClientSecret
        every {
            authenticationGateway.refresh(RefreshTokenInput(expectedClientId, expectedClientSecret, "ref"))
        } returns AuthenticationResult(expectedAccessToken, expectedRefreshToken)

        // when
        manager.refresh()

        val actualCredentials = ReflectionTestUtils.getField(manager, "credentials") as ClientCredentials

        // then
        assertEquals(expectedAccessToken, actualCredentials.accessToken)
        assertEquals(expectedRefreshToken, actualCredentials.refreshToken)
    }

    @Test
    fun givenAnErrorFromRefreshToken_whenCallsRefresh_shouldFallbackToLogin() {
        // given
        val expectedAccessToken = "accessToken"
        val expectedRefreshToken = "refreshToken"
        val expectedClientId = "client-id"
        val expectedClientSecret = "client-secret"

        ReflectionTestUtils.setField(manager, "credentials", ClientCredentials(expectedClientId, "acc", "ref"))

        every { keycloakProperties.clientId } returns expectedClientId
        every { keycloakProperties.clientSecret } returns expectedClientSecret
        every {
            authenticationGateway.refresh(RefreshTokenInput(expectedClientId, expectedClientSecret, "ref"))
        } throws InternalErrorException.with("Doesn't matter")
        every {
            authenticationGateway.login(ClientCredentialsInput(expectedClientId, expectedClientSecret))
        } returns AuthenticationResult(expectedAccessToken, expectedRefreshToken)

        // when
        manager.refresh()

        val actualCredentials = ReflectionTestUtils.getField(manager, "credentials") as ClientCredentials

        // then
        assertEquals(expectedAccessToken, actualCredentials.accessToken)
        assertEquals(expectedRefreshToken, actualCredentials.refreshToken)
    }
}
