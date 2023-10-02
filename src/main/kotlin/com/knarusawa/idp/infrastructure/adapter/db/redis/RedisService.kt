package com.knarusawa.idp.infrastructure.adapter.db.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService(
  private val redisTemplate: RedisTemplate<String, Any>,
) {
  fun saveObject(key: String, data: Any, ttl: Long?, timeUnit: TimeUnit) {
    redisTemplate.opsForValue().set(key, data, ttl ?: 600L, timeUnit)
  }

  fun getObject(key: String): Any? {
    return redisTemplate.opsForValue().get(key)
  }

  fun deleteObject(key: String) {
    redisTemplate.delete(key)
  }
}