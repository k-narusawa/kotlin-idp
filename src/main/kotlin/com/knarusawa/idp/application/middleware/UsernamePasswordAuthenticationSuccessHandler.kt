package com.knarusawa.idp.application.middleware

import com.knarusawa.idp.domain.model.authority.AuthorityRole
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

class UsernamePasswordAuthenticationSuccessHandler(
  private val nextAuthUrl: String,
  defaultTargetUrl: String
) : SimpleUrlAuthenticationSuccessHandler(defaultTargetUrl) {
  override fun onAuthenticationSuccess(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    authentication: Authentication
  ) {
    val isUsingMfa = authentication.authorities?.any {
      it.authority == AuthorityRole.MFA_MAIL.toString()
    } ?: false

    if (isUsingMfa) {
      response?.sendRedirect(nextAuthUrl)
    } else {
      super.onAuthenticationSuccess(request, response, authentication)
    }
  }
}