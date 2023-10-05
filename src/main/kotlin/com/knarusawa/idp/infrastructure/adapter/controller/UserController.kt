package com.knarusawa.idp.infrastructure.adapter.controller

import com.knarusawa.idp.application.service.changeUserLoginId.ChangeUserLoginIdInputData
import com.knarusawa.idp.application.service.changeUserLoginId.ChangeUserLoginIdService
import com.knarusawa.idp.application.service.changeUserPassword.ChangeUserPasswordInputData
import com.knarusawa.idp.application.service.changeUserPassword.ChangeUserPasswordService
import com.knarusawa.idp.application.service.query.UserActivityDtoQueryService
import com.knarusawa.idp.application.service.query.UserDtoQueryService
import com.knarusawa.idp.application.service.withdrawUser.WithdrawUserInputData
import com.knarusawa.idp.application.service.withdrawUser.WithdrawUserService
import com.knarusawa.idp.domain.model.IdpAppException
import com.knarusawa.idp.domain.value.ErrorCode
import com.knarusawa.idp.infrastructure.dto.form.ChangeUserLoginIdForm
import com.knarusawa.idp.infrastructure.dto.form.ChangeUserPasswordForm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("user")
@PreAuthorize("hasRole('USER')")
class UserController(
        private val userDtoQueryService: UserDtoQueryService,
        private val userActivityDtoQueryService: UserActivityDtoQueryService,
        private val changeUserLoginIdService: ChangeUserLoginIdService,
        private val changeUserPasswordService: ChangeUserPasswordService,
        private val withdrawUserService: WithdrawUserService,
) {
    @GetMapping("/activities")
    fun getProfile(
            model: Model,
            authentication: Authentication,
            @RequestParam page: Int?
    ): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        val userActivities = userActivityDtoQueryService.findByUserId(
                userId = contextUser.userId.toString(),
                pageable = PageRequest.of(page ?: 0, 10, Sort.by("timestamp").descending())
        )
        model.addAttribute("userActivities", userActivities)
        model.addAttribute("totalPages", userActivities.totalPages)
        return "user/user_activity_list"
    }

    @GetMapping("/login_id/change")
    fun changeLoginId(
            model: Model,
            authentication: Authentication
    ): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        val user = userDtoQueryService.findByUserId(userId = contextUser.userId.toString())
                ?: throw IdpAppException(errorCode = ErrorCode.USER_NOT_FOUND, logMessage = "UserNot Found.")

        model.addAttribute("currentLoginId", user.loginId)
        return "user/user_login_id_change"
    }

    @PostMapping("/login_id/change")
    fun changeLoginId(
            authentication: Authentication,
            @ModelAttribute changeUserLoginIdForm: ChangeUserLoginIdForm,
    ): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        val inputData = ChangeUserLoginIdInputData(
                userId = contextUser.userId.toString(),
                loginId = changeUserLoginIdForm.loginId,
        )
        changeUserLoginIdService.execute(input = inputData)
        return "redirect:/"
    }

    @GetMapping("/password/change")
    fun changePassword(model: Model): String {
        return "user/user_password_change"
    }

    @PostMapping("/password/change")
    fun changePassword(
            authentication: Authentication,
            @ModelAttribute changeUserPasswordForm: ChangeUserPasswordForm,
    ): String {
        if (changeUserPasswordForm.newPassword != changeUserPasswordForm.confirmPassword) {
            return "user/user_password_change"
        }

        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        val inputData = ChangeUserPasswordInputData(
                userId = contextUser.userId.toString(),
                password = changeUserPasswordForm.confirmPassword,
        )
        changeUserPasswordService.execute(input = inputData)
        return "redirect:/"
    }

    @PostMapping("/withdraw")
    fun withdraw(
            request: HttpServletRequest,
            authentication: Authentication
    ): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        withdrawUserService.exec(input = WithdrawUserInputData(userId = contextUser.userId.toString()))
        request.logout()
        return "redirect:/"
    }
}