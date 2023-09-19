package com.knarusawa.idp.configuration

import com.knarusawa.idp.infrastructure.adapter.db.repository.CredentialRepository
import com.warrenstrange.googleauth.GoogleAuthenticator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig : WebMvcConfigurer {
  @Autowired
  private lateinit var credentialRepository: CredentialRepository

  @Bean
  fun authenticationEventPublisher(applicationEventPublisher: ApplicationEventPublisher?): AuthenticationEventPublisher {
//    val mapping: Map<Class<out AuthenticationException>, Class<out AbstractAuthenticationFailureEvent>> =
//      mapOf(Pair(AppException::class.java, AuthenticationEvents::class.java))
    val authenticationEventPublisher =
      DefaultAuthenticationEventPublisher(applicationEventPublisher)
//    authenticationEventPublisher.setAdditionalExceptionMappings(mapping)
    return authenticationEventPublisher
  }

  @Bean
  fun gAuth(): GoogleAuthenticator {
    val googleAuthenticator = GoogleAuthenticator()
    googleAuthenticator.setCredentialRepository(credentialRepository)
    return googleAuthenticator
  }
}