package com.knarusawa.idp.configuration

import com.knarusawa.idp.application.middleware.MfaAuthenticationHandler
import com.knarusawa.idp.application.service.UserDetailsServiceImpl
import com.knarusawa.idp.configuration.db.UserDbJdbcTemplate
import com.knarusawa.idp.domain.model.authentication.MfaAuthentication
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.core.Authentication
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
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import java.util.function.Supplier


@Configuration
class SecurityConfig {
  @Autowired
  private lateinit var userDetailsServiceImpl: UserDetailsServiceImpl

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
            .requestMatchers("/login").permitAll()
            .requestMatchers("/login/mfa").access(mfaAuthorizationManager())
            .requestMatchers("/api/user/*").permitAll()  //細かい制御は@PreAuthorizedで行う
            .requestMatchers("/api/admin/*").permitAll() //細かい制御は@PreAuthorizedで行う
            .requestMatchers("/user/*").permitAll()  //細かい制御は@PreAuthorizedで行う
            .requestMatchers("/admin/*").permitAll() //細かい制御は@PreAuthorizedで行う
            .requestMatchers("/register").permitAll() // 会員登録画面
            .requestMatchers("webjars/**", "/css/**", "/js/**", "/img/**").permitAll()
            .anyRequest().authenticated()
        }
      )
      .authenticationManager(authManager(http))
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
          .successHandler(MfaAuthenticationHandler("/login/mfa"))
          .failureHandler(MfaAuthenticationHandler("/login/mfa"))
      }
      .securityContext { context ->
        context.requireExplicitSave(false)
      }
      .logout { logout ->
        logout
          .addLogoutHandler(CookieClearingLogoutHandler("JSESSIONID"))
      }
    return http.build()
  }

  @Bean
  fun authManager(http: HttpSecurity): AuthenticationManager {
    val authenticationManagerBuilder = http.getSharedObject(
      AuthenticationManagerBuilder::class.java
    )
    val daoAuthenticationProvider = DaoAuthenticationProvider().also {
      it.setUserDetailsService(userDetailsServiceImpl)
      it.setPasswordEncoder(passwordEncoder())
    }

    authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider)
    return authenticationManagerBuilder.build()
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

  @Bean
  fun mfaAuthorizationManager(): AuthorizationManager<RequestAuthorizationContext> {
    return AuthorizationManager { authentication: Supplier<Authentication?>, _: RequestAuthorizationContext? ->
      AuthorizationDecision(
        authentication.get() is MfaAuthentication
      )
    }
  }

  @Bean
  fun successHandler(): AuthenticationSuccessHandler {
    return SavedRequestAwareAuthenticationSuccessHandler()
  }

  @Bean
  fun failureHandler(): AuthenticationFailureHandler {
    return SimpleUrlAuthenticationFailureHandler("/login?error")
  }
}