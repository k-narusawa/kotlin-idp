package com.knarusawa.idp.infrastructure.controller

import com.knarusawa.idp.application.service.UserDtoQueryService
import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeInputData
import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeService
import com.knarusawa.idp.application.service.changeUserPassword.UserPasswordChangeInputData
import com.knarusawa.idp.application.service.changeUserPassword.UserPasswordChangeService
import com.knarusawa.idp.domain.model.error.AppException
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.infrastructure.dto.ChangeUserLoginIdForm
import com.knarusawa.idp.infrastructure.dto.ChangeUserPasswordForm
import java.security.Principal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("user")
@PreAuthorize("hasRole('USER')")
class UserController(
  private val userDtoQueryService: UserDtoQueryService,
  private val userLoginIdChangeService: UserLoginIdChangeService,
  private val userPasswordChangeService: UserPasswordChangeService
) {
  @GetMapping("/login_id/change")
  fun changeLoginId(
    model: Model,
    principal: Principal
  ): String {
    val user = userDtoQueryService.findByUserId(userId = principal.name)
      ?: throw AppException(errorCode = ErrorCode.USER_NOT_FOUND, logMessage = "UserNot Found.")

    model.addAttribute("currentLoginId", user.loginId)
    return "user_login_id_change"
  }

  @PostMapping("/login_id/change")
  fun changeLoginId(
    @ModelAttribute changeUserLoginIdForm: ChangeUserLoginIdForm,
    principal: Principal
  ): String {
    val inputData = UserLoginIdChangeInputData(
      userId = principal.name,
      loginId = changeUserLoginIdForm.loginId,
    )
    userLoginIdChangeService.execute(input = inputData)
    return "redirect:/"
  }

  @GetMapping("/password/change")
  fun changePassword(
    model: Model,
  ): String {
    return "user_password_change"
  }

  @PostMapping("/password/change")
  fun changePassword(
    @ModelAttribute changeUserPasswordForm: ChangeUserPasswordForm,
    principal: Principal
  ): String {
    if (changeUserPasswordForm.newPassword != changeUserPasswordForm.confirmPassword) {
      return "user_password_change"
    }
    val inputData = UserPasswordChangeInputData(
      userId = principal.name,
      password = changeUserPasswordForm.confirmPassword,
    )
    userPasswordChangeService.execute(input = inputData)
    return "redirect:/"
  }
}