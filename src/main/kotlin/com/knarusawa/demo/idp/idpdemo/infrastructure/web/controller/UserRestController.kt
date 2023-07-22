package com.knarusawa.demo.idp.idpdemo.infrastructure.web.controller

import com.knarusawa.demo.idp.idpdemo.application.mapper.UserMapper
import com.knarusawa.demo.idp.idpdemo.application.service.user.getByUserId.UserGetByUserIdService
import com.knarusawa.demo.idp.idpdemo.application.service.user.loginIdUpdate.UserLoginIdUpdateInputData
import com.knarusawa.demo.idp.idpdemo.application.service.user.loginIdUpdate.UserLoginIdUpdateService
import com.knarusawa.demo.idp.idpdemo.application.service.user.passwordChange.UserPasswordChangeInputData
import com.knarusawa.demo.idp.idpdemo.application.service.user.passwordChange.UserPasswordChangeService
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserResponse
import java.security.Principal
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserRestController(
  private val userGetByUserIdService: UserGetByUserIdService,
  private val userLoginIdUpdateService: UserLoginIdUpdateService,
  private val userPasswordChangeService: UserPasswordChangeService
) {
  @GetMapping
  @PreAuthorize("hasRole('USER')")
  fun getUser(principal: Principal): UserResponse {
    val userId = principal.name
    val user = userGetByUserIdService.execute(userId = userId).user
    return UserMapper.fromUser(user)
  }

  @PutMapping("/login_id")
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

  @PutMapping("/password")
  @PreAuthorize("hasRole('USER')")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  fun updateUserPassword(principal: Principal, password: String) {
    val userId = principal.name
    userPasswordChangeService.execute(
      UserPasswordChangeInputData(
        userId = userId,
        password = password
      )
    )
  }
}