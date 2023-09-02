package com.knarusawa.idp.infrastructure.controller

import com.knarusawa.idp.application.service.UserDtoQueryService
import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeInputData
import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeService
import com.knarusawa.idp.domain.model.error.AppException
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.infrastructure.dto.ChangeUserLoginIdForm
import java.security.Principal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("user")
class UserController(
  private val userLoginIdChangeService: UserLoginIdChangeService,
  private val userDtoQueryService: UserDtoQueryService,
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
}