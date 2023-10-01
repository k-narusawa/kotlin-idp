package com.knarusawa.idp.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.knarusawa.idp.domain.model.authority.IdpGrantedAuthority
import com.knarusawa.idp.infrastructure.adapter.db.repository.CredentialRepository
import com.warrenstrange.googleauth.GoogleAuthenticator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.jackson2.CoreJackson2Module
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
  fun objectMapper(): ObjectMapper {
    val mapper = ObjectMapper()
    mapper.registerModule(CoreJackson2Module())
    mapper.addMixIn(
      IdpGrantedAuthority::class.java,
      SecurityConfig.IdpGrantedAuthorityMixin::class.java
    )
    mapper.deserializationConfig.withoutFeatures(
      com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
    )
    return mapper
  }

  @Bean
  fun gAuth(): GoogleAuthenticator {
    val googleAuthenticator = GoogleAuthenticator()
    googleAuthenticator.setCredentialRepository(credentialRepository)
    return googleAuthenticator
  }
}