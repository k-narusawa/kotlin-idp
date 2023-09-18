package com.knarusawa.idp.infrastructure.adapter.controllers

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.knarusawa.idp.application.service.generateGAuth.GenerateGAuthInput
import com.knarusawa.idp.application.service.generateGAuth.GenerateGAuthService
import com.knarusawa.idp.application.service.registerGAuth.RegisterGAuthInput
import com.knarusawa.idp.application.service.registerGAuth.RegisterGAuthService
import com.knarusawa.idp.application.service.sendOtp.SendOtpInput
import com.knarusawa.idp.application.service.sendOtp.SendOtpService
import com.knarusawa.idp.application.service.verifyOtp.VerifyOtpInput
import com.knarusawa.idp.application.service.verifyOtp.VerifyOtpService
import jakarta.servlet.http.HttpServletResponse
import java.security.Principal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@PreAuthorize("hasRole('USER')")
class UserMfaController(
  private val generateGAuthService: GenerateGAuthService,
  private val registerGAuthService: RegisterGAuthService,
  private val sendOtpService: SendOtpService,
  private val verifyOtpService: VerifyOtpService,
) {
  @GetMapping("/user/mfa")
  fun mfa(): String {
    return "user_mfa"
  }

  @GetMapping("/user/mfa/app")
  fun mfaApp(): String {
    return "user_mfa_app"
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
    registerGAuthService.exec(input)
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