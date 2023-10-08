package com.knarusawa.idp.config

import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession


@Configuration
@EnableRedisIndexedHttpSession
class SessionConfig