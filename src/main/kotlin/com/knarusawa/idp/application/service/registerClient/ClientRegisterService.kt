package com.knarusawa.idp.application.service.registerClient

import com.knarusawa.idp.domain.model.Client
import com.knarusawa.idp.infrastructure.adapter.db.repository.ExtendedRegisteredClientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClientRegisterService(
        private val extendedRegisteredClientRepository: ExtendedRegisteredClientRepository
) {
    @Transactional
    fun execute(input: ClientRegisterInputData) {
        println(input)
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