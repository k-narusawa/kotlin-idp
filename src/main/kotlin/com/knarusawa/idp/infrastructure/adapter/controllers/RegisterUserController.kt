package com.knarusawa.idp.infrastructure.adapter.controllers

import com.knarusawa.idp.application.service.registerUser.UserRegisterInputData
import com.knarusawa.idp.application.service.registerUser.UserRegisterService
import com.knarusawa.idp.infrastructure.dto.form.UserForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RegisterUserController(
  private val userRegisterService: UserRegisterService,
) {
  @GetMapping("/register")
  fun registerUser(model: Model): String {
    model.addAttribute("userForm", UserForm("", "", ""))
    return "user_register"
  }

  @PostMapping("/register")
  fun registerUser(@ModelAttribute userForm: UserForm): String {
    val command = UserRegisterInputData(
      loginId = userForm.loginId,
      password = userForm.password,
      eMail = userForm.eMail
    )
    userRegisterService.execute(command)
    return "redirect:/"
  }
}