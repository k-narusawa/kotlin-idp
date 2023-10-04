package com.knarusawa.idp.infrastructure.adapter.db.repository.tmpUser

import com.knarusawa.idp.domain.model.oneTimePassword.Code
import com.knarusawa.idp.domain.model.tmpUser.TmpUser
import com.knarusawa.idp.domain.model.user.LoginId
import com.knarusawa.idp.domain.repository.TmpUserRepository
import com.knarusawa.idp.infrastructure.adapter.db.redis.RedisService
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class TmpUserRepositoryImpl(
        private val redisService: RedisService
) : TmpUserRepository {
    override fun save(tmpUser: TmpUser) {
        redisService.saveObject(tmpUser.code, tmpUser, tmpUser.ttl, TimeUnit.SECONDS)
    }

    override fun findByCode(code: Code): TmpUser? {
        return redisService.getObject(code.toString()) as? TmpUser
    }

    override fun deleteByLoginId(loginId: LoginId) {
        redisService.deleteObject(loginId.toString())
    }
}