package com.knarusawa.demo.idp.idpdemo.configuration

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
  @Bean
  fun authenticationEventPublisher(applicationEventPublisher: ApplicationEventPublisher?): AuthenticationEventPublisher {
//    val mapping: Map<Class<out AuthenticationException>, Class<out AbstractAuthenticationFailureEvent>> =
//      mapOf(Pair(AppException::class.java, AuthenticationEvents::class.java))
    val authenticationEventPublisher =
      DefaultAuthenticationEventPublisher(applicationEventPublisher)
//    authenticationEventPublisher.setAdditionalExceptionMappings(mapping)
    return authenticationEventPublisher
  }
}