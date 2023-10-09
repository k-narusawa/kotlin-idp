package com.knarusawa.idp.infrastructure.adapter.db.repository

import com.knarusawa.idp.domain.model.LoginIdUpdateDate
import com.knarusawa.idp.domain.repository.LoginIdUpdateDateRepository
import com.knarusawa.idp.domain.value.LoginIdUpdateKey
import com.knarusawa.idp.domain.value.LoginIdUpdateValue
import com.knarusawa.idp.infrastructure.adapter.db.redis.RedisService
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class LoginIdUpdateDateRepositoryImpl(
        private val redisService: RedisService
) : LoginIdUpdateDateRepository {
    override fun save(data: LoginIdUpdateDate) {
        redisService.saveObject(data.key.toString(), data.value, data.ttl, TimeUnit.SECONDS)
    }

    override fun findByKey(key: String): LoginIdUpdateValue? {
        return redisService.getObject(key) as? LoginIdUpdateValue
    }

    override fun deleteByKey(key: LoginIdUpdateKey) {
        redisService.deleteObject(key.toString())
    }
}