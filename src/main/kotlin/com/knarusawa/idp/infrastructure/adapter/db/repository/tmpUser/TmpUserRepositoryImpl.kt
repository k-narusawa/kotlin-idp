package com.knarusawa.idp.infrastructure.adapter.db.repository.tmpUser

import com.knarusawa.idp.domain.model.tmpUser.TmpUser
import com.knarusawa.idp.domain.model.user.LoginId
import com.knarusawa.idp.domain.repository.TmpUserRepository
import com.knarusawa.idp.infrastructure.adapter.db.redis.RedisService
import org.springframework.stereotype.Repository

@Repository
class TmpUserRepositoryImpl(
  private val redisService: RedisService
) : TmpUserRepository {
  override fun save(tmpUser: TmpUser) {
    redisService.saveObject(tmpUser.loginId.toString(), "test")
  }

  override fun findByLoginId(loginId: LoginId): TmpUser? {
    return redisService.getObject(loginId.toString()) as? TmpUser
  }

  override fun deleteByLoginId(loginId: LoginId) {
    redisService.deleteObject(loginId.toString())
  }
}