package com.knarusawa.idp.infrastructure.adapter.controller

import com.knarusawa.idp.application.service.registerTmpUser.RegisterTmpUserInputData
import com.knarusawa.idp.application.service.registerTmpUser.RegisterTmpUserService
import com.knarusawa.idp.application.service.registerUser.UserRegisterInputData
import com.knarusawa.idp.application.service.registerUser.UserRegisterService
import com.knarusawa.idp.infrastructure.dto.form.TmpUserForm
import com.knarusawa.idp.infrastructure.dto.form.UserForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RegisterUserController(
  private val registerTmpUserService: RegisterTmpUserService,
  private val userRegisterService: UserRegisterService,
) {

  @GetMapping("/tmp_register")
  fun tmpRegisterUser(model: Model): String {
    return "tmp_user_register"
  }

  @PostMapping("/tmp_register")
  fun tmpRegisterUser(@ModelAttribute tmpUserForm: TmpUserForm): String {
    val input = RegisterTmpUserInputData(
      loginId = tmpUserForm.loginId,
    )
    registerTmpUserService.exec(input)
    return "user_register"
  }

  @GetMapping("/register")
  fun registerUser(model: Model): String {
    return "user_register"
  }

  @PostMapping("/register")
  fun registerUser(@ModelAttribute userForm: UserForm): String {
    val input = UserRegisterInputData(
      code = userForm.code,
      password = userForm.password,
    )
    userRegisterService.execute(input)
    return "redirect:/"
  }
}