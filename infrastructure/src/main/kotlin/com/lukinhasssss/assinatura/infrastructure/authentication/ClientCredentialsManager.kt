package com.lukinhasssss.assinatura.infrastructure.authentication

import com.lukinhasssss.assinatura.infrastructure.authentication.AuthenticationGateway.ClientCredentialsInput
import com.lukinhasssss.assinatura.infrastructure.authentication.AuthenticationGateway.RefreshTokenInput
import com.lukinhasssss.assinatura.infrastructure.configuration.properties.KeycloakProperties
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater

@Component
class ClientCredentialsManager(
    private val authenticationGateway: AuthenticationGateway,
    private val keycloakProperties: KeycloakProperties,
) : GetClientCredentials, RefreshClientCredentials {
    companion object {
        private val UPDATER =
            AtomicReferenceFieldUpdater.newUpdater(
                ClientCredentialsManager::class.java,
                ClientCredentials::class.java,
                "credentials",
            )
    }

    @Volatile
    private var credentials: ClientCredentials? = null

    override fun retrieve() = credentials!!.accessToken

    override fun refresh() =
        with(keycloakProperties) {
            val result =
                if (credentials == null) {
                    login()
                } else {
                    refreshToken()
                }
            UPDATER[this@ClientCredentialsManager] = ClientCredentials(clientId, result.accessToken, result.refreshToken)
        }

    private fun KeycloakProperties.login() = authenticationGateway.login(ClientCredentialsInput(clientId, clientSecret))

    private fun KeycloakProperties.refreshToken() =
        try {
            authenticationGateway.refresh(RefreshTokenInput(clientId, clientSecret, credentials!!.refreshToken))
        } catch (ex: RuntimeException) {
            login()
        }

    data class ClientCredentials(val clientId: String, val accessToken: String, val refreshToken: String)
}
