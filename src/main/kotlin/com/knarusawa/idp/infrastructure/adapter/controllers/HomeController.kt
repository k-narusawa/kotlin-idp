package com.knarusawa.idp.infrastructure.adapter.controllers

import com.knarusawa.idp.application.service.UserActivityDtoQueryService
import com.knarusawa.idp.application.service.UserDtoQueryService
import java.security.Principal
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(
  private val userDtoQueryService: UserDtoQueryService,
  private val userActivityDtoQueryService: UserActivityDtoQueryService,
) {
  @GetMapping("/")
  fun getProfile(model: Model, principal: Principal): String {
    val user = userDtoQueryService.findByUserId(userId = principal.name)
    val userActivities = userActivityDtoQueryService.findByUserId(
      userId = principal.name,
      pageable = PageRequest.of(0, 3, Sort.by("timestamp").descending())
    )
    model.addAttribute("user", user)
    model.addAttribute("userActivities", userActivities)
    return "index"
  }
}