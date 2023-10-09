package com.knarusawa.idp.infrastructure.adapter.controller

import com.knarusawa.idp.application.service.requestResetUserPassword.RequestResetUserPasswordSInputData
import com.knarusawa.idp.application.service.requestResetUserPassword.RequestResetUserPasswordService
import com.knarusawa.idp.application.service.resetUserPassword.ResetUserPasswordInputData
import com.knarusawa.idp.application.service.resetUserPassword.ResetUserPasswordService
import com.knarusawa.idp.infrastructure.dto.form.RequestResetPasswordForm
import com.knarusawa.idp.infrastructure.dto.form.ResetPasswordForm
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class PasswordResetController(
        private val requestResetUserPasswordService: RequestResetUserPasswordService,
        private val resetUserPasswordService: ResetUserPasswordService,
) {
    @GetMapping("/password/reset/request")
    fun requestResetPassword(): String {
        return "user_password_reset_request"
    }

    @PostMapping("/password/reset/request")
    fun requestResetPasswordPost(
            @ModelAttribute form: RequestResetPasswordForm
    ): String {
        val input = RequestResetUserPasswordSInputData(form.loginId)
        requestResetUserPasswordService.exec(input)
        return "redirect:/password/reset"
    }

    @GetMapping("/password/reset")
    fun resetPassword(): String {
        return "user_password_reset"
    }

    @PostMapping("/password/reset")
    fun resetPasswordPost(
            @ModelAttribute form: ResetPasswordForm
    ): String {
        val input = ResetUserPasswordInputData(code = form.code, password = form.password)
        resetUserPasswordService.exec(input)
        return "redirect:/"
    }
}