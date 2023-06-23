package com.knarusawa.demo.idp.idpdemo.configuration.db

import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.JdbcTransactionManager

@Configuration
class UserDbJdbcTemplate(dataSource: UserDbDataSource) : JdbcTemplate(dataSource)

@Configuration
class UserDbTransactionManager(dataSource: UserDbDataSource) : JdbcTransactionManager(dataSource)