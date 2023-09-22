package com.knarusawa.idp.infrastructure.adapter.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ErrorController {
  @GetMapping("/error/403")
  fun accessDenied(): String {
    return "error/403"
  }
}