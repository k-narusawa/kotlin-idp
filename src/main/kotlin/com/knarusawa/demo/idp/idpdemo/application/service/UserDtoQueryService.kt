package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserDtoQueryService : JpaRepository<UserDto, Int> {
  override fun findAll(): List<UserDto>

  fun findByUserId(userId: String): UserDto
}