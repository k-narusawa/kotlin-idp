package com.knarusawa.demo.idp.idpdemo.configuration

import com.knarusawa.demo.idp.idpdemo.configuration.db.UserDbJdbcTemplate
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler


@Configuration
class SecurityConfig {
  companion object {
    private fun generateRsaKey(): KeyPair {
      val keyPair: KeyPair = try {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        keyPairGenerator.generateKeyPair()
      } catch (ex: Exception) {
        throw IllegalStateException(ex)
      }
      return keyPair
    }
  }

  @Bean
  @Order(1)
  fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)
    http.getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
      .oidc(Customizer.withDefaults())
    http
      .exceptionHandling { exceptions: ExceptionHandlingConfigurer<HttpSecurity?> ->
        exceptions
          .authenticationEntryPoint(
            LoginUrlAuthenticationEntryPoint("/login")
          )
      }
      .oauth2ResourceServer { obj ->
        obj.jwt(Customizer.withDefaults())
      }
    return http.build()
  }

  @Bean
  @Order(2)
  fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .authorizeHttpRequests(
        Customizer { authorize ->
          authorize
            .requestMatchers("/api/user/*").permitAll()  //細かい制御は@PreAuthorizedで行う
            .requestMatchers("/api/admin*").permitAll() //細かい制御は@PreAuthorizedで行う
            .anyRequest().authenticated()
        }
      )
      .oauth2ResourceServer { oauth2ResourceServer ->
        oauth2ResourceServer
          .jwt { jwt ->
            jwt.decoder(jwtDecoder(jwkSource()))
          }
      }
      .formLogin { form: FormLoginConfigurer<HttpSecurity?> ->
        form
          .loginPage("/login")
          .permitAll()
      }
      .logout { logout ->
        logout.addLogoutHandler(CookieClearingLogoutHandler("JSESSIONID"))
      }
    return http.build()
  }

  @Bean
  fun jwkSource(): JWKSource<SecurityContext> {
    val keyPair = generateRsaKey()
    val publicKey = keyPair.public as RSAPublicKey
    val privateKey = keyPair.private as RSAPrivateKey
    val rsaKey: RSAKey = RSAKey.Builder(publicKey)
      .privateKey(privateKey)
      .keyID(UUID.randomUUID().toString())
      .build()
    val jwkSet = JWKSet(rsaKey)
    return ImmutableJWKSet(jwkSet)
  }

  @Bean
  fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
  }

  @Bean
  fun authorizationServerSettings(): AuthorizationServerSettings {
    return AuthorizationServerSettings.builder()
      .build()
  }

  @Bean
  fun authorizationService(
    jdbcTemplate: UserDbJdbcTemplate,
    registeredClientRepository: RegisteredClientRepository
  ): OAuth2AuthorizationService {
    return JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository)
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}