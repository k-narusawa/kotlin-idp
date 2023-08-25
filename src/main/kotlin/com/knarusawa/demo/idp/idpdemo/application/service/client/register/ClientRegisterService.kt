package com.knarusawa.demo.idp.idpdemo.application.service.client.register

import com.knarusawa.demo.idp.idpdemo.domain.model.client.Client
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository.ExtendedRegisteredClientRepository
import org.springframework.stereotype.Service

@Service
class ClientRegisterService(
  private val extendedRegisteredClientRepository: ExtendedRegisteredClientRepository
) {
  fun execute(input: ClientRegisterInputData) {
    val client = Client.of(
      clientId = input.clientId,
      clientSecret = input.clientSecret,
      clientAuthenticationMethods = input.clientAuthenticationMethods,
      clientAuthenticationGrantTypes = input.clientAuthenticationGrantTypes,
      redirectUrls = input.redirectUrls,
      scopes = input.scopes,
    )
    extendedRegisteredClientRepository.save(client.ofRegisteredClient())
  }
}