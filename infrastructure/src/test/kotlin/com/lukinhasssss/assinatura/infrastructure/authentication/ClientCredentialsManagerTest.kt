package com.lukinhasssss.assinatura.infrastructure.authentication

import com.lukinhasssss.assinatura.domain.exception.InternalErrorException
import com.lukinhasssss.assinatura.infrastructure.authentication.AuthenticationGateway.AuthenticationResult
import com.lukinhasssss.assinatura.infrastructure.authentication.AuthenticationGateway.ClientCredentialsInput
import com.lukinhasssss.assinatura.infrastructure.authentication.AuthenticationGateway.RefreshTokenInput
import com.lukinhasssss.assinatura.infrastructure.authentication.ClientCredentialsManager.ClientCredentials
import com.lukinhasssss.assinatura.infrastructure.configuration.properties.KeycloakProperties
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.util.ReflectionTestUtils

@ExtendWith(MockKExtension::class)
class ClientCredentialsManagerTest : FunSpec({
    val keycloakProperties = mockk<KeycloakProperties>()
    val authenticationGateway = mockk<AuthenticationGateway>()
    val manager = ClientCredentialsManager(authenticationGateway, keycloakProperties)

    test("given a valid authentication result, when calls refresh, should create credentials") {
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
        actualToken shouldBe expectedAccessToken
    }

    test("given previous authentication, when calls refresh, should update credentials") {
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
        actualCredentials.accessToken shouldBe expectedAccessToken
        actualCredentials.refreshToken shouldBe expectedRefreshToken
    }

    test("given an error from refresh token, when calls refresh, should fallback to login") {
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
        actualCredentials.accessToken shouldBe expectedAccessToken
        actualCredentials.refreshToken shouldBe expectedRefreshToken
    }
})
