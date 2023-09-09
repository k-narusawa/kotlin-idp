package com.knarusawa.idp.application.service.query

import com.knarusawa.idp.application.dto.UserMailDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserMailDtoQueryService : JpaRepository<UserMailDto, Int> {
  fun findByUserId(userId: String): UserMailDto?
}