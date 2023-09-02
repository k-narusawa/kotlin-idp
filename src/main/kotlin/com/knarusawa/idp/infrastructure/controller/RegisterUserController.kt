package com.knarusawa.idp.infrastructure.controller

import com.knarusawa.idp.application.service.registerUser.UserRegisterInputData
import com.knarusawa.idp.application.service.registerUser.UserRegisterService
import com.knarusawa.idp.domain.model.user.Role
import com.knarusawa.idp.infrastructure.dto.UserForm
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
    model.addAttribute("userForm", UserForm("", ""))
    model.addAttribute("roleItems", Role.items())
    return "user_register"
  }

  @PostMapping("/register")
  fun registerUser(@ModelAttribute userForm: UserForm): String {
    val command = UserRegisterInputData(
      loginId = userForm.loginId,
      password = userForm.password,
    )
    userRegisterService.execute(command)
    return "redirect:/"
  }
}