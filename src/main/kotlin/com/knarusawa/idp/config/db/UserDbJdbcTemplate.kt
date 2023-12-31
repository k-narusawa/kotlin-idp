package com.knarusawa.idp.config.db

import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class UserDbJdbcTemplate(dataSource: UserDbDataSource) : JdbcTemplate(dataSource)