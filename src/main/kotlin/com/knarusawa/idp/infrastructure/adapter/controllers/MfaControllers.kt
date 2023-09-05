package com.knarusawa.idp.infrastructure.adapter.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MfaControllers {
  @GetMapping("/login/mfa")
  fun mfa(): String {
    return "mfa"
  }
}