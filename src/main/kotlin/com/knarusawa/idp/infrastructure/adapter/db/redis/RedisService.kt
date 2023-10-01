package com.knarusawa.idp.infrastructure.adapter.db.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
  private val redisTemplate: RedisTemplate<String, Any>,
) {
  fun saveObject(key: String, data: Any) {
    redisTemplate.opsForValue().set(key, data)
  }

  fun getObject(key: String): Any? {
    return redisTemplate.opsForValue().get(key)
  }

  fun deleteObject(key: String) {
    redisTemplate.delete(key)
  }
}