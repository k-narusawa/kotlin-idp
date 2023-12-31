package com.knarusawa.idp.infrastructure.adapter.controller

import com.knarusawa.idp.application.service.query.UserMfaDtoQueryService
import com.knarusawa.idp.application.service.verifyOtp.VerifyOtpInputData
import com.knarusawa.idp.application.service.verifyOtp.VerifyOtpService
import com.knarusawa.idp.domain.model.IdpAppException
import com.knarusawa.idp.domain.model.MfaAuthentication
import com.knarusawa.idp.domain.value.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
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
        return "login/mfa"
    }

    @PostMapping("/login/mfa")
    fun mfaAuth(
            @RequestParam("code") code: String,
            mfaAuthentication: MfaAuthentication,
            authentication: Authentication,
            request: HttpServletRequest,
            response: HttpServletResponse
    ) {
        val primaryAuthentication = mfaAuthentication.getFirst()
        val user = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw IdpAppException(errorCode = ErrorCode.BAD_REQUEST, logMessage = "User not found")

        val userMfa = userMfaDtoQueryService.findByUserId(user.userId.toString())
                ?: throw IdpAppException(errorCode = ErrorCode.BAD_REQUEST, logMessage = "MFAが未設定")

        val isVerified =
                verifyOtpService.exec(
                        input = VerifyOtpInputData(
                                userId = user.userId.toString(),
                                mfaType = userMfa.mfaType,
                                code = code
                        )
                )

        if (isVerified) {
            SecurityContextHolder.getContext().authentication = primaryAuthentication
            successHandler.onAuthenticationSuccess(request, response, mfaAuthentication)
        } else {
            failureHandler.onAuthenticationFailure(
                    request,
                    response,
                    BadCredentialsException("MFA検証失敗")
            )
        }
    }
}