package com.knarusawa.idp.application.service

import com.knarusawa.idp.application.dto.UserActivityDto
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserActivityDtoQueryService : JpaRepository<UserActivityDto, Int> {
  fun findByUserId(userId: String, pageable: Pageable): List<UserActivityDto>
}