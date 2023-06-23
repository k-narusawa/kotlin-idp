package com.knarusawa.demo.idp.idpdemo.configuration.db

import com.zaxxer.hikari.HikariConfig
import org.springframework.context.annotation.Configuration

@Configuration
class UserDbHikariConfig : HikariConfig() {
  init {
    jdbcUrl = "jdbc:mysql://127.0.0.1:3306/user_db"
    username = "root"
    password = "root"
    driverClassName = "com.mysql.cj.jdbc.Driver"
    maximumPoolSize = 10
    isAutoCommit = true
  }
}