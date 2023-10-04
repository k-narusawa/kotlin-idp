package com.knarusawa.idp.application.service.query

import com.knarusawa.idp.application.dto.UserDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserDtoQueryService : JpaRepository<UserDto, Int> {
    override fun findAll(): List<UserDto>

    fun findByUserId(userId: String): UserDto?
}