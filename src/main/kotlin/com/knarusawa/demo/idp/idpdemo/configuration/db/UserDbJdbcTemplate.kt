package com.knarusawa.demo.idp.idpdemo.configuration.db

import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class UserDbJdbcTemplate(dataSource: UserDbDataSource) : JdbcTemplate(dataSource)