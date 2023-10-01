package com.knarusawa.idp.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
  @Autowired
  private lateinit var environments: Environments

  @Bean
  fun redisConnectionFactory(): LettuceConnectionFactory {
    return LettuceConnectionFactory(
      RedisStandaloneConfiguration(
        environments.redisHost,
        environments.redisPort.toInt()
      )
    )
  }

  @Bean
  fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
    val template = RedisTemplate<String, Any>()
    template.connectionFactory = redisConnectionFactory
    return template
  }

  @Bean
  fun redisSerializer(objectMapper: ObjectMapper): Jackson2JsonRedisSerializer<Any> {
    val serializer = Jackson2JsonRedisSerializer(Any::class.java)
    serializer.setObjectMapper(objectMapper) // FIXME: deprecatedなのでライブラリの中身確認して修正したい
    return serializer
  }

  @Bean
  fun redisSerializationContext(jackson2JsonRedisSerializer: Jackson2JsonRedisSerializer<Any>): RedisSerializationContext<String, Any> {
    return RedisSerializationContext
      .newSerializationContext<String, Any>(StringRedisSerializer())
      .value(jackson2JsonRedisSerializer)
      .build()
  }
}