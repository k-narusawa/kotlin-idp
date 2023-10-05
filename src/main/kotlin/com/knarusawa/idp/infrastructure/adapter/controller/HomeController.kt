package com.knarusawa.idp.infrastructure.adapter.controller

import com.knarusawa.idp.application.service.query.UserActivityDtoQueryService
import com.knarusawa.idp.application.service.query.UserDtoQueryService
import com.knarusawa.idp.application.service.query.UserMfaDtoQueryService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
@PreAuthorize("hasRole('USER')")
class HomeController(
        private val userDtoQueryService: UserDtoQueryService,
        private val userMfaDtoQueryService: UserMfaDtoQueryService,
        private val userActivityDtoQueryService: UserActivityDtoQueryService,
) {
    @GetMapping("/")
    fun getProfile(model: Model, authentication: Authentication): String {
        val contextUser = authentication.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("コンテキストから会員の取得に失敗しました")

        val user = userDtoQueryService.findByUserId(userId = contextUser.userId.toString())
        val userMfa = userMfaDtoQueryService.findByUserId(userId = contextUser.userId.toString())
        val userActivities = userActivityDtoQueryService.findByUserId(
                userId = contextUser.userId.toString(),
                pageable = PageRequest.of(0, 3, Sort.by("timestamp").descending())
        )

        model.addAttribute("user", user)
        model.addAttribute("userMfa", userMfa)
        model.addAttribute("userActivities", userActivities)
        return "index"
    }
}