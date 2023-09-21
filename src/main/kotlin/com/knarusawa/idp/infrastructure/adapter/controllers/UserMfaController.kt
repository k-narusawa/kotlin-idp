package com.knarusawa.idp.infrastructure.adapter.controllers

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.knarusawa.idp.application.service.generateGAuth.GenerateGAuthInput
import com.knarusawa.idp.application.service.generateGAuth.GenerateGAuthService
import com.knarusawa.idp.application.service.invalidateMfa.InvalidateMfaInputData
import com.knarusawa.idp.application.service.invalidateMfa.InvalidateMfaService
import com.knarusawa.idp.application.service.registerGAuth.RegisterGAuthInput
import com.knarusawa.idp.application.service.registerGAuth.RegisterGAuthService
import com.knarusawa.idp.application.service.registerMfa.RegisterMfaInput
import com.knarusawa.idp.application.service.registerMfa.RegisterMfaService
import com.knarusawa.idp.application.service.sendOtp.SendOtpInput
import com.knarusawa.idp.application.service.sendOtp.SendOtpService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal


@Controller
@PreAuthorize("hasRole('USER')")
class UserMfaController(
  private val generateGAuthService: GenerateGAuthService,
  private val registerGAuthService: RegisterGAuthService,
  private val sendOtpService: SendOtpService,
  private val registerMfaService: RegisterMfaService,
  private val invalidateMfaService: InvalidateMfaService
) {
  @GetMapping("/user/mfa")
  fun mfa(): String {
    return "user/user_mfa"
  }

  @DeleteMapping("/user/mfa")
  fun deleteMfa(
    principal: Principal,
  ): String {
    invalidateMfaService.exec(input = InvalidateMfaInputData(userId = principal.name))
    return "redirect:/"
  }

  @GetMapping("/user/mfa/app")
  fun mfaApp(): String {
    return "user/user_mfa_app"
  }

  @GetMapping("/user/mfa/app/qr")
  fun mfaAppQr(
    principal: Principal,
    response: HttpServletResponse
  ) {
    val input = GenerateGAuthInput(userId = principal.name)

    val qrCodeWriter = QRCodeWriter()

    val otpAuthUrl = generateGAuthService.exec(input = input)
    val bitMatrix = qrCodeWriter.encode(otpAuthUrl, BarcodeFormat.QR_CODE, 200, 200)

    val outputStream = response.outputStream
    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
    outputStream.close()
  }

  @PostMapping("/user/mfa/app")
  fun registerMfaApp(
    principal: Principal,
    @RequestParam("code") code: String,
  ): String {
    val input = RegisterGAuthInput(
      userId = principal.name,
      code = code
    )

    val result = registerGAuthService.exec(input)

    if (!result)
      return "user/user_mfa_app"

    return "redirect:/"
  }

  @GetMapping("/user/mfa/mail")
  fun mfaMail(
    principal: Principal,
  ): String {
    sendOtpService.exec(input = SendOtpInput(userId = principal.name))
    return "user/user_mfa_mail"
  }

  @PostMapping("/user/mfa/mail")
  fun registerMfaMail(
    principal: Principal,
    @RequestParam("code") code: String,
  ): String {
    val result =
      registerMfaService.exec(input = RegisterMfaInput(userId = principal.name, code = code))

    if (!result) {
      return "user/user_mfa_mail"
    }
    return "redirect:/"
  }
}