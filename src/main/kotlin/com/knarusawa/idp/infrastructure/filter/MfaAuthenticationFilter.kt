package com.knarusawa.idp.infrastructure.filter

import com.knarusawa.idp.domain.model.AssertionAuthenticationToken
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
      throw AuthenticationServiceException("Authentication method not supported: " + request.method)
    }

    val otp = getOtp(request)
    verifyOtp(otp = otp)
    val principal = getPrincipal(request)

    val credentials = AssertionAuthenticationToken.MfaCredentials(sessionId = "")

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
    val otp = request.getParameter("code")

    if (otp.isNullOrEmpty()) {
      throw AuthenticationServiceException("code is invalid.")
    }
    return otp
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

  /**
   * OTPの検証を行う
   *
   * @param otp ワンタイムパスワード
   */
  private fun verifyOtp(otp: String) {
    if (otp != "123456") {
      throw AuthenticationServiceException("code is invalid.")
    }
  }
}