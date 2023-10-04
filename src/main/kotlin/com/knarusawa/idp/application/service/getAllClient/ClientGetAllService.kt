package com.knarusawa.idp.application.service.getAllClient

import com.knarusawa.idp.domain.model.client.Client
import com.knarusawa.idp.infrastructure.adapter.db.repository.ExtendedRegisteredClientRepository
import org.springframework.stereotype.Service

@Service
class ClientGetAllService(
        private val extendedRegisteredClientRepository: ExtendedRegisteredClientRepository
) {
    fun execute(): List<Client> {
        return extendedRegisteredClientRepository.findAll().map { Client.fromRegisteredClient(it) }
    }
}