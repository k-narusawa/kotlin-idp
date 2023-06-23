package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.client.Client
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository.ExtendedRegisteredClientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ClientService(
  private val extendedRegisteredClientRepository: ExtendedRegisteredClientRepository
) {
  fun registerClient(client: Client) {
    extendedRegisteredClientRepository.save(client.ofRegisteredClient())
  }

  fun getClients(): List<Client> {
    return extendedRegisteredClientRepository.findAll().map { Client.fromRegisteredClient(it) }
  }
}