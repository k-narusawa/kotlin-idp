package com.knarusawa.idp.infrastructure.adapter.db.repository

import com.knarusawa.idp.domain.model.PasswordResetData
import com.knarusawa.idp.domain.repository.PasswordResetDataRepository
import com.knarusawa.idp.domain.value.PasswordResetKey
import com.knarusawa.idp.domain.value.PasswordResetValue
import com.knarusawa.idp.infrastructure.adapter.db.redis.RedisService
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class PasswordResetDataRepositoryImpl(
        private val redisService: RedisService
) : PasswordResetDataRepository {
    override fun save(data: PasswordResetData) {
        redisService.saveObject(data.key.toString(), data.value, data.ttl, TimeUnit.SECONDS)
    }

    override fun findByKey(key: String): PasswordResetValue? {
        return redisService.getObject(key) as? PasswordResetValue
    }

    override fun deleteByKey(key: PasswordResetKey) {
        redisService.deleteObject(key.toString())
    }
}