package com.knarusawa.idp.infrastructure.adapter.controllers

import com.knarusawa.idp.application.service.CheckMfaService
import com.knarusawa.idp.domain.model.authentication.MfaAuthentication
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MfaController(
  private val successHandler: AuthenticationSuccessHandler,
  private val failureHandler: AuthenticationFailureHandler,
  private val checkMfaService: CheckMfaService
) {
  @GetMapping("/login/mfa")
  fun mfa(): String {
    return "mfa"
  }

  @PostMapping("/login/mfa")
  fun mfaAuth(
    @RequestParam("code") code: String,
    authentication: MfaAuthentication,
    request: HttpServletRequest,
    response: HttpServletResponse
  ) {
    val primaryAuthentication = authentication.getFirst()

    val isVerified = checkMfaService.exec(userId = primaryAuthentication.name, otp = code)

    if (isVerified) {
      SecurityContextHolder.getContext().authentication = primaryAuthentication
      successHandler.onAuthenticationSuccess(request, response, authentication)
    } else {
      failureHandler.onAuthenticationFailure(request, response, BadCredentialsException("MFA検証失敗"))
    }
  }
}