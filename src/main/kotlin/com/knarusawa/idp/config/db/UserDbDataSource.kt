package com.knarusawa.idp.config.db

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Configuration

@Configuration
class UserDbDataSource(hikariConfig: UserDbHikariConfig) : HikariDataSource(hikariConfig)