package com.knarusawa.demo.idp.idpdemo.domain.repository.user

import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {
  fun findByUserId(userId: String): UserEntity?
  fun findByLoginId(loginId: String): UserEntity?

}