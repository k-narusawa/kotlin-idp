package com.knarusawa.idp.infrastructure.controller

import com.knarusawa.idp.application.dto.UserDto
import com.knarusawa.idp.application.service.UserDtoQueryService
import com.knarusawa.idp.application.service.changeUserLoginId.UserLoginIdChangeService
import com.knarusawa.idp.application.service.changeUserPassword.UserPasswordChangeService
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import java.security.Principal
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
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
      ?: throw IdpAppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        logMessage = "User Not Found."
      )
  }
}