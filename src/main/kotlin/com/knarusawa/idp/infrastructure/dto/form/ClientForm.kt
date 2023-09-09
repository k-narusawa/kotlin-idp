package com.knarusawa.idp.infrastructure.dto.form

import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes

data class ClientForm(
  val clientId: String,
  val clientSecret: String,
  val clientAuthenticationMethods: List<ClientAuthenticationMethodForm>,
  val clientAuthenticationGrantTypes: List<AuthorizationGrantTypeForm>,
  val redirectUrls: String,
  val scopes: List<OidcScopesForm>,
) {
  enum class ClientAuthenticationMethodForm {
    CLIENT_SECRET_BASIC,
    CLIENT_SECRET_POST,
    CLIENT_SECRET_JWT,
    ;

    companion object {
      fun items(): Map<String, ClientAuthenticationMethodForm> {
        return ClientAuthenticationMethodForm.values().associateBy { it.name }
      }
    }

    fun to(): ClientAuthenticationMethod = when (this) {
      CLIENT_SECRET_BASIC -> ClientAuthenticationMethod.CLIENT_SECRET_BASIC
      CLIENT_SECRET_POST -> ClientAuthenticationMethod.CLIENT_SECRET_POST
      CLIENT_SECRET_JWT -> ClientAuthenticationMethod.CLIENT_SECRET_JWT
    }
  }

  enum class AuthorizationGrantTypeForm {
    AUTHORIZATION_CODE,
    CLIENT_CREDENTIALS,
    REFRESH_TOKEN,
    ;

    companion object {
      fun items(): Map<String, AuthorizationGrantTypeForm> {
        return AuthorizationGrantTypeForm.values().associateBy { it.name }
      }
    }

    fun to(): AuthorizationGrantType = when (this) {
      AUTHORIZATION_CODE -> AuthorizationGrantType.AUTHORIZATION_CODE
      CLIENT_CREDENTIALS -> AuthorizationGrantType.CLIENT_CREDENTIALS
      REFRESH_TOKEN -> AuthorizationGrantType.REFRESH_TOKEN
    }
  }

  enum class OidcScopesForm {
    OPENID,
    PROFILE,
    EMAIL,
    ADDRESS,
    PHONE,
    ;

    companion object {
      fun items(): Map<String, OidcScopesForm> {
        return OidcScopesForm.values().associateBy { it.name }
      }
    }

    fun to(): String = when (this) {
      OPENID -> OidcScopes.OPENID
      PROFILE -> OidcScopes.PROFILE
      EMAIL -> OidcScopes.EMAIL
      ADDRESS -> OidcScopes.ADDRESS
      PHONE -> OidcScopes.PHONE
    }
  }

}
