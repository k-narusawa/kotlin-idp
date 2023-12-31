package com.knarusawa.idp.infrastructure.adapter.controller

import com.knarusawa.idp.application.dto.UserDto
import com.knarusawa.idp.application.service.query.UserDtoQueryService
import com.knarusawa.idp.domain.model.IdpAppException
import com.knarusawa.idp.domain.value.ErrorCode
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/user")
class UserRestController(
        private val userDtoQueryService: UserDtoQueryService,
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