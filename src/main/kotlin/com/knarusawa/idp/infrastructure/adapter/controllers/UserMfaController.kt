package com.knarusawa.idp.infrastructure.adapter.controllers

import com.knarusawa.idp.application.service.sendOtp.SendOtpInput
import com.knarusawa.idp.application.service.sendOtp.SendOtpService
import com.knarusawa.idp.application.service.verifyOtp.VerifyOtpInput
import com.knarusawa.idp.application.service.verifyOtp.VerifyOtpService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal


@Controller
@PreAuthorize("hasRole('USER')")
class UserMfaController(
  private val sendOtpService: SendOtpService,
  private val verifyOtpService: VerifyOtpService,
) {
  @GetMapping("/user/mfa")
  fun mfa(): String {
    return "user_mfa"
  }

  @GetMapping("/user/mfa/app")
  fun mfaApp(
    @RequestParam("code") code: String,
  ): String {
    return "user_mfa_app"
  }

  @PostMapping("/user/mfa/app")
  fun registerMfaApp(
    @RequestParam("code") code: String,
  ): String {
    return "redirect:/"
  }

  @GetMapping("/user/mfa/mail")
  fun mfaMail(
    principal: Principal,
  ): String {
    sendOtpService.exec(input = SendOtpInput(userId = principal.name))
    return "user_mfa_mail"
  }

  @PostMapping("/user/mfa/mail")
  fun registerMfaMail(
    principal: Principal,
    @RequestParam("code") code: String,
  ): String {
    val result = verifyOtpService.exec(input = VerifyOtpInput(userId = principal.name, code = code))

    if (!result) {
      return "user_mfa_mail"
    }
    return "redirect:/"
  }
}