package com.knarusawa.idp.infrastructure.adapter.controller

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.knarusawa.idp.application.service.generateGAuth.GenerateGAuthInputData
import com.knarusawa.idp.application.service.generateGAuth.GenerateGAuthService
import com.knarusawa.idp.application.service.invalidateMfa.InvalidateMfaInputData
import com.knarusawa.idp.application.service.invalidateMfa.InvalidateMfaService
import com.knarusawa.idp.application.service.registerGAuth.RegisterGAuthInputData
import com.knarusawa.idp.application.service.registerGAuth.RegisterGAuthService
import com.knarusawa.idp.application.service.registerMfa.RegisterMfaInputData
import com.knarusawa.idp.application.service.registerMfa.RegisterMfaService
import com.knarusawa.idp.application.service.sendOtp.SendOtpInputData
import com.knarusawa.idp.application.service.sendOtp.SendOtpService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


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
    fun deleteMfa(authentication: Authentication): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        invalidateMfaService.exec(input = InvalidateMfaInputData(userId = contextUser.userId.toString()))
        return "redirect:/"
    }

    @GetMapping("/user/mfa/app")
    fun mfaApp(): String {
        return "user/user_mfa_app"
    }

    @GetMapping("/user/mfa/app/qr")
    fun mfaAppQr(
            authentication: Authentication,
            response: HttpServletResponse
    ) {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        val input = GenerateGAuthInputData(userId = contextUser.userId.toString())

        val qrCodeWriter = QRCodeWriter()

        val otpAuthUrl = generateGAuthService.exec(input = input)
        val bitMatrix = qrCodeWriter.encode(otpAuthUrl, BarcodeFormat.QR_CODE, 200, 200)

        val outputStream = response.outputStream
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
        outputStream.close()
    }

    @PostMapping("/user/mfa/app")
    fun registerMfaApp(
            authentication: Authentication,
            @RequestParam("code") code: String,
    ): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        val input = RegisterGAuthInputData(userId = contextUser.userId.toString(), code = code)

        val result = registerGAuthService.exec(input)

        if (!result)
            return "user/user_mfa_app"

        return "redirect:/"
    }

    @GetMapping("/user/mfa/mail")
    fun mfaMail(authentication: Authentication): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        sendOtpService.exec(input = SendOtpInputData(userId = contextUser.userId.toString()))
        return "user/user_mfa_mail"
    }

    @PostMapping("/user/mfa/mail")
    fun registerMfaMail(
            authentication: Authentication,
            @RequestParam("code") code: String,
    ): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        val result =
                registerMfaService.exec(input = RegisterMfaInputData(userId = contextUser.userId.toString(), code = code))

        if (!result) {
            return "user/user_mfa_mail"
        }
        return "redirect:/"
    }
}