package com.knarusawa.demo.idp.idpdemo.infrastructure.web.controller

import com.knarusawa.demo.idp.idpdemo.application.mapper.UserMapper
import com.knarusawa.demo.idp.idpdemo.application.service.user.changeLoginId.UserLoginIdChangeInputData
import com.knarusawa.demo.idp.idpdemo.application.service.user.changeLoginId.UserLoginIdChangeService
import com.knarusawa.demo.idp.idpdemo.application.service.user.changePassword.UserPasswordChangeInputData
import com.knarusawa.demo.idp.idpdemo.application.service.user.changePassword.UserPasswordChangeService
import com.knarusawa.demo.idp.idpdemo.application.service.user.getByUserId.UserGetByUserIdService
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserResponse
import java.security.Principal
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserRestController(
  private val userGetByUserIdService: UserGetByUserIdService,
  private val userLoginIdChangeService: UserLoginIdChangeService,
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
  fun updateUserLoginId(
    principal: Principal,
    @RequestBody loginId: String
  ): UserResponse {
    val userId = principal.name
    val user = userLoginIdChangeService.execute(
      UserLoginIdChangeInputData(
        userId = userId,
        loginId = loginId
      )
    ).user
    return UserMapper.fromUser(user)
  }

  @PutMapping("/password")
  @PreAuthorize("hasRole('USER')")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  fun updateUserPassword(
    principal: Principal,
    @RequestBody password: String
  ) {
    val userId = principal.name
    userPasswordChangeService.execute(
      UserPasswordChangeInputData(
        userId = userId,
        password = password
      )
    )
  }
}