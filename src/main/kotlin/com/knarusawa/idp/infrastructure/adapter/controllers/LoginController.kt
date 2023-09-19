package com.knarusawa.idp.infrastructure.adapter.controllers

import com.knarusawa.idp.application.service.query.UserMfaDtoQueryService
import com.knarusawa.idp.application.service.verifyOtp.VerifyOtpInput
import com.knarusawa.idp.application.service.verifyOtp.VerifyOtpService
import com.knarusawa.idp.domain.model.authentication.MfaAuthentication
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping
class LoginController(
  private val userMfaDtoQueryService: UserMfaDtoQueryService,
  private val successHandler: AuthenticationSuccessHandler,
  private val failureHandler: AuthenticationFailureHandler,
  private val verifyOtpService: VerifyOtpService,
) {
  @GetMapping("/login")
  fun login(): String {
    return "login"
  }

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
    val userId = primaryAuthentication.name

    val userMfa = userMfaDtoQueryService.findByUserId(userId)
      ?: throw IdpAppException(errorCode = ErrorCode.BAD_REQUEST, logMessage = "MFAが未設定")

    val isVerified =
      verifyOtpService.exec(
        input = VerifyOtpInput(
          userId = userId,
          mfaType = userMfa.mfaType,
          code = code
        )
      )

    if (isVerified) {
      SecurityContextHolder.getContext().authentication = primaryAuthentication
      successHandler.onAuthenticationSuccess(request, response, authentication)
    } else {
      failureHandler.onAuthenticationFailure(
        request,
        response,
        BadCredentialsException("MFA検証失敗")
      )
    }
  }
}