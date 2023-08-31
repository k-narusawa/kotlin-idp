package com.knarusawa.demo.idp.idpdemo.application.service.registerClient

import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

data class ClientRegisterInputData(
  val clientId: String,
  val clientSecret: String,
  val clientAuthenticationMethods: List<ClientAuthenticationMethod>,
  val clientAuthenticationGrantTypes: List<AuthorizationGrantType>,
  val redirectUrls: List<String>,
  val scopes: List<String>,
)