package com.knarusawa.idp.infrastructure.adapter.controller

import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeInputData
import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeService
import com.knarusawa.idp.application.service.changeUserPassword.UserPasswordChangeInputData
import com.knarusawa.idp.application.service.changeUserPassword.UserPasswordChangeService
import com.knarusawa.idp.application.service.query.UserActivityDtoQueryService
import com.knarusawa.idp.application.service.query.UserDtoQueryService
import com.knarusawa.idp.application.service.withdrawUser.WithdrawUserInputData
import com.knarusawa.idp.application.service.withdrawUser.WithdrawUserService
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.infrastructure.dto.form.ChangeUserLoginIdForm
import com.knarusawa.idp.infrastructure.dto.form.ChangeUserPasswordForm
import jakarta.servlet.http.HttpServletRequest
import java.security.Principal
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("user")
@PreAuthorize("hasRole('USER')")
class UserController(
  private val userDtoQueryService: UserDtoQueryService,
  private val userActivityDtoQueryService: UserActivityDtoQueryService,
  private val userLoginIdChangeService: UserLoginIdChangeService,
  private val userPasswordChangeService: UserPasswordChangeService,
  private val withdrawUserService: WithdrawUserService,
) {
  @GetMapping("/activities")
  fun getProfile(
    model: Model,
    principal: Principal,
    @RequestParam page: Int?
  ): String {
    val userActivities = userActivityDtoQueryService.findByUserId(
      userId = principal.name,
      pageable = PageRequest.of(page ?: 0, 10, Sort.by("timestamp").descending())
    )
    model.addAttribute("userActivities", userActivities)
    model.addAttribute("totalPages", userActivities.totalPages)
    return "user/user_activity_list"
  }

  @GetMapping("/login_id/change")
  fun changeLoginId(
    model: Model,
    principal: Principal
  ): String {
    val user = userDtoQueryService.findByUserId(userId = principal.name)
      ?: throw IdpAppException(errorCode = ErrorCode.USER_NOT_FOUND, logMessage = "UserNot Found.")

    model.addAttribute("currentLoginId", user.loginId)
    return "user/user_login_id_change"
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
    return "user/user_password_change"
  }

  @PostMapping("/password/change")
  fun changePassword(
    @ModelAttribute changeUserPasswordForm: ChangeUserPasswordForm,
    principal: Principal
  ): String {
    if (changeUserPasswordForm.newPassword != changeUserPasswordForm.confirmPassword) {
      return "user/user_password_change"
    }
    val inputData = UserPasswordChangeInputData(
      userId = principal.name,
      password = changeUserPasswordForm.confirmPassword,
    )
    userPasswordChangeService.execute(input = inputData)
    return "redirect:/"
  }

  @PostMapping("/withdraw")
  fun withdraw(
    request: HttpServletRequest,
    principal: Principal
  ): String {
    withdrawUserService.exec(input = WithdrawUserInputData(userId = principal.name))
    request.logout()
    return "redirect:/"
  }
}