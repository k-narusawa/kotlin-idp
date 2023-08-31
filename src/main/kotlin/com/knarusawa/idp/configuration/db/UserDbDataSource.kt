package com.knarusawa.idp.configuration.db

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Configuration

@Configuration
class UserDbDataSource(hikariConfig: UserDbHikariConfig) : HikariDataSource(hikariConfig)