package com.knarusawa.idp.infrastructure.filter

import com.knarusawa.idp.domain.model.assertion.AssertionAuthenticationToken
import com.knarusawa.idp.domain.model.assertion.MfaCredentials
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class MfaAuthenticationFilter(
  pattern: String,
  httpMethod: String,
) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(pattern, httpMethod)) {
  override fun attemptAuthentication(
    request: HttpServletRequest?,
    response: HttpServletResponse?
  ): Authentication {
    if (request!!.method != "POST") {
      throw AuthenticationServiceException("2段階認証において不正なリクエストです Method:" + request.method)
    }

    val otp = getOtp(request)
    val principal = getPrincipal(request)

    val credentials = MfaCredentials(code = otp)

    val authorities = principal.authorities.map {
      SimpleGrantedAuthority(it.authority)
    }

    val authRequest = AssertionAuthenticationToken(principal, credentials, authorities)
    authRequest.details = authenticationDetailsSource.buildDetails(request)

    return authenticationManager.authenticate(authRequest)
  }

  /**
   * リクエストからワンタイムパスワードを取得する
   *
   * @param request リクエスト
   * @return ワンタイムパスワード
   */
  private fun getOtp(request: HttpServletRequest): String {
    return request.getParameter("code")
      ?: throw AuthenticationServiceException("code is invalid.")
  }

  private fun getPrincipal(request: HttpServletRequest): User {
    val session = request.session
    val securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT") as SecurityContext
    val principal = securityContext.authentication.principal
    if (principal !is User) {
      throw AuthenticationServiceException("principal is invalid.")
    }
    return principal
  }
}