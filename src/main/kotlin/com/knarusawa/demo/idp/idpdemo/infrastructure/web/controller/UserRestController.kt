package com.knarusawa.demo.idp.idpdemo.infrastructure.web.controller

import com.knarusawa.demo.idp.idpdemo.application.mapper.UserMapper
import com.knarusawa.demo.idp.idpdemo.application.service.UserRoleService
import com.knarusawa.demo.idp.idpdemo.application.service.UserService
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserResponse
import java.security.Principal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserRestController(
  private val userService: UserService,
  private val userRoleService: UserRoleService
) {
  @GetMapping
  @PreAuthorize("hasRole('USER')")
  fun getUser(principal: Principal): UserResponse {
    val userId = principal.name
    val user = userService.getByUserId(userId = userId)
    val userRole = userRoleService.getUserRole(userId = userId)
    return UserMapper.fromUser(user, userRole.map { it.role })
  }
}