package com.knarusawa.idp.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class Environments {
    companion object {
        private const val LOCAL_PROFILE = "local"
    }

    @Value("\${environments.db.crypt-key}")
    lateinit var cryptKey: String

    @Value("\${environments.db.salt}")
    lateinit var cryptSalt: String

    @Value("\${environments.mail.from}")
    lateinit var fromAddress: String

    @Value("\${spring.data.redis.host}")
    lateinit var redisHost: String

    @Value("\${spring.data.redis.port}")
    lateinit var redisPort: String
}