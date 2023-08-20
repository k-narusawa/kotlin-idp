package com.knarusawa.demo.idp.idpdemo.domain.model.client

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import java.util.*
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings

class Client private constructor(
  val id: String,
  var clientId: String,
  var clientSecret: String,
  var clientAuthenticationMethods: List<ClientAuthenticationMethod>,
  var clientAuthenticationGrantTypes: List<AuthorizationGrantType>,
  var redirectUrls: List<String>,
  var scopes: List<String>,
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
    fun of(
      clientId: String,
      clientSecret: String,
      clientAuthenticationMethods: List<ClientAuthenticationMethod>,
      clientAuthenticationGrantTypes: List<AuthorizationGrantType>,
      redirectUrls: List<String>,
      scopes: List<String>,
    ) = Client(
      id = UUID.randomUUID().toString(),
      clientId = clientId,
      clientSecret = clientSecret,
      clientAuthenticationMethods = clientAuthenticationMethods,
      clientAuthenticationGrantTypes = clientAuthenticationGrantTypes,
      redirectUrls = redirectUrls,
      scopes = scopes,
    )

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
