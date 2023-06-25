package com.knarusawa.demo.idp.idpdemo.application.mapper

import com.knarusawa.demo.idp.idpdemo.domain.model.client.Client
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.ClientForm
import java.util.*

object ClientMapper {
  fun toClient(clientForm: ClientForm) = Client(
    id = UUID.randomUUID().toString(),
    clientId = clientForm.clientId,
    clientSecret = clientForm.clientSecret,
    clientAuthenticationMethods = clientForm.clientAuthenticationMethods.map { it.to() },
    clientAuthenticationGrantTypes = clientForm.clientAuthenticationGrantTypes.map { it.to() },
    redirectUrls = clientForm.redirectUrls.split(",").map { it.trim() }.filter { it.isNotEmpty() },
    scopes = clientForm.scopes.map { it.to() },
  )
}