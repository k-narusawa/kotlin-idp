package com.knarusawa.idp.infrastructure.adapter.controllers

import com.knarusawa.idp.application.service.query.UserActivityDtoQueryService
import com.knarusawa.idp.application.service.query.UserDtoQueryService
import com.knarusawa.idp.application.service.query.UserMailDtoQueryService
import com.knarusawa.idp.application.service.query.UserMfaDtoQueryService
import java.security.Principal
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
@PreAuthorize("hasRole('USER')")
class HomeController(
  private val userDtoQueryService: UserDtoQueryService,
  private val userMfaDtoQueryService: UserMfaDtoQueryService,
  private val userMailDtoQueryService: UserMailDtoQueryService,
  private val userActivityDtoQueryService: UserActivityDtoQueryService,
) {
  @GetMapping("/")
  fun getProfile(model: Model, principal: Principal): String {
    val user = userDtoQueryService.findByUserId(userId = principal.name)
    val userMfa = userMfaDtoQueryService.findByUserId(userId = principal.name)
    val userActivities = userActivityDtoQueryService.findByUserId(
      userId = principal.name,
      pageable = PageRequest.of(0, 3, Sort.by("timestamp").descending())
    )
    val userMail = userMailDtoQueryService.findByUserId(userId = principal.name)

    model.addAttribute("user", user)
    model.addAttribute("userMfa", userMfa)
    model.addAttribute("userMail", userMail)
    model.addAttribute("userActivities", userActivities)
    return "index"
  }
}