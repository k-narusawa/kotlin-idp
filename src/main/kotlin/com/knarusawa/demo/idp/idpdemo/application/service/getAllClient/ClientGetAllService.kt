package com.knarusawa.demo.idp.idpdemo.application.service.getAllClient

import com.knarusawa.demo.idp.idpdemo.domain.model.client.Client
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository.ExtendedRegisteredClientRepository
import org.springframework.stereotype.Service

@Service
class ClientGetAllService(
  private val extendedRegisteredClientRepository: ExtendedRegisteredClientRepository
) {
  fun execute(): List<Client> {
    return extendedRegisteredClientRepository.findAll().map { Client.fromRegisteredClient(it) }
  }
}