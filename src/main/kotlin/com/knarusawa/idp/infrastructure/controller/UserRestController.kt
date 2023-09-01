package com.knarusawa.idp.infrastructure.controller

import com.knarusawa.idp.application.mapper.UserMapper
import com.knarusawa.idp.application.service.UserDtoQueryService
import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeInputData
import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeService
import com.knarusawa.idp.application.service.changeUserPassword.UserPasswordChangeInputData
import com.knarusawa.idp.application.service.changeUserPassword.UserPasswordChangeService
import com.knarusawa.idp.domain.model.error.AppException
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.infrastructure.dto.UserDto
import com.knarusawa.idp.infrastructure.dto.UserResponse
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
  private val userDtoQueryService: UserDtoQueryService,
  private val userLoginIdChangeService: UserLoginIdChangeService,
  private val userPasswordChangeService: UserPasswordChangeService
) {
  @GetMapping
  @PreAuthorize("hasRole('USER')")
  fun getUser(principal: Principal): UserDto {
    val userId = principal.name
    return userDtoQueryService.findByUserId(userId = userId)
      ?: throw AppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        logMessage = "User Not Found."
      )
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