package com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository

import com.knarusawa.demo.idp.idpdemo.domain.repository.UserWithRoleRepository
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserWithRoleEntity
import org.springframework.stereotype.Repository

@Repository
class UserWithRoleRepositoryImpl : UserWithRoleRepository {
  companion object {
    private val COLUMN_NAMES = ("user_id, "
        + "login_id,"
        + "password,"
        + "is_lock,"
        + "failed_attempts,"
        + "lock_time,"
        + "is_disabled,"
        + "created_at,"
        + "updated_at")
  }

  override fun findByLoginId(loginId: String): UserWithRoleEntity? {
    TODO("Not yet implemented")
  }
}