package com.knarusawa.demo.idp.idpdemo.configuration.db

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Configuration

@Configuration
class UserDbDataSource(hikariConfig: UserDbHikariConfig) : HikariDataSource(hikariConfig)