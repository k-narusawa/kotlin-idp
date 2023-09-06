package com.knarusawa.idp.application.middleware

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

    val needFido = true

    if (needFido) {
      response?.sendRedirect(nextAuthUrl)
    } else {
      super.onAuthenticationSuccess(request, response, authentication)
    }
  }
}