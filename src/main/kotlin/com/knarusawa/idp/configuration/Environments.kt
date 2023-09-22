package com.knarusawa.idp.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class Environments {
  companion object {
    private const val LOCAL_PROFILE = "local"
  }


  @Value("\${environments.mail.from}")
  lateinit var fromAddress: String


}