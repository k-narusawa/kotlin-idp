package com.knarusawa.idp.application.service.query

import com.knarusawa.idp.application.dto.UserMfaDto
import org.springframework.data.repository.CrudRepository

interface UserMfaDtoQueryService : CrudRepository<UserMfaDto, String> {
    fun findByUserId(userId: String): UserMfaDto?
}