package com.knarusawa.demo.idp.idpdemo.domain.model.client

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings

data class Client(
  val id: String,
  val clientId: String,
  val clientSecret: String,
  val clientAuthenticationMethods: List<ClientAuthenticationMethod>,
  val clientAuthenticationGrantTypes: List<AuthorizationGrantType>,
  val redirectUrls: List<String>,
  val scopes: List<String>,
) {
  fun ofRegisteredClient(): RegisteredClient {
    return RegisteredClient
      .withId(id)
      .clientId(clientId)
      .clientSecret(SecurityConfig().passwordEncoder().encode(clientSecret))
      .clientAuthenticationMethods { it.addAll(clientAuthenticationMethods) }
      .authorizationGrantTypes { it.addAll(clientAuthenticationGrantTypes) }
      .redirectUris { it.addAll(redirectUrls) }
      .scopes { it.addAll(scopes.map { scope -> scope.toString() }) }
      .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
      .build()
  }

  companion object {
    fun fromRegisteredClient(registeredClient: RegisteredClient): Client {
      return Client(
        id = registeredClient.id,
        clientId = registeredClient.clientId,
        clientSecret = registeredClient.clientSecret ?: "",
        clientAuthenticationMethods = registeredClient.clientAuthenticationMethods.toList(),
        clientAuthenticationGrantTypes = registeredClient.authorizationGrantTypes.toList(),
        redirectUrls = registeredClient.redirectUris.toList(),
        scopes = registeredClient.scopes.toList()
      )
    }
  }
}
