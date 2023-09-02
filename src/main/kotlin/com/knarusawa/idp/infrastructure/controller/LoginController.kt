package com.knarusawa.idp.infrastructure.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
class LoginController {
  @GetMapping("/login")
  fun login(): String {
    return "login"
  }
}