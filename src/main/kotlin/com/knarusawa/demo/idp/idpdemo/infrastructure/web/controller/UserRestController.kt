package com.knarusawa.demo.idp.idpdemo.infrastructure.web.controller

import com.knarusawa.demo.idp.idpdemo.application.mapper.UserMapper
import com.knarusawa.demo.idp.idpdemo.application.service.user.getByUserId.UserGetByUserIdService
import com.knarusawa.demo.idp.idpdemo.application.service.user.loginIdUpdate.UserLoginIdUpdateInputData
import com.knarusawa.demo.idp.idpdemo.application.service.user.loginIdUpdate.UserLoginIdUpdateService
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/user")
class UserRestController(
  private val userGetByUserIdService: UserGetByUserIdService,
  private val userLoginIdUpdateService: UserLoginIdUpdateService
) {
  @GetMapping
  @PreAuthorize("hasRole('USER')")
  fun getUser(principal: Principal): UserResponse {
    val userId = principal.name
    val user = userGetByUserIdService.execute(userId = userId).user
    return UserMapper.fromUser(user)
  }

  @PutMapping
  @PreAuthorize("hasRole('USER')")
  fun updateUserLoginId(principal: Principal, loginId: String): UserResponse {
    val userId = principal.name
    val user = userLoginIdUpdateService.execute(
      UserLoginIdUpdateInputData(
        userId = userId,
        loginId = loginId
      )
    ).user
    return UserMapper.fromUser(user)
  }
}