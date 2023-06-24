package com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository

import com.knarusawa.demo.idp.idpdemo.configuration.db.UserDbJdbcTemplate
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserRecord
import java.sql.ResultSet
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository


@Repository
class UserRepositoryImpl(
  private val userDbJdbcTemplate: UserDbJdbcTemplate
) : UserRepository {
  companion object {
    val userRowMapper = RowMapper { rs: ResultSet, _: Int ->
      UserRecord(
        userId = rs.getString("user_id"),
        loginId = rs.getString("login_id"),
        password = rs.getString("password"),
        isLock = rs.getBoolean("is_lock"),
        failedAttempts = rs.getInt("failed_attempts"),
        lockTime = rs.getTimestamp("lock_time")?.toLocalDateTime(),
        isDisabled = rs.getBoolean("is_disabled"),
        createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
        updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
      )
    }
  }

  override fun save(user: UserRecord): UserRecord {
    val insertQuery =
      "INSERT INTO user (user_id, login_id, password, is_lock, failed_attempts, lock_time, is_disabled) VALUES (?, ?, ?, ?, ?, ?, ?)"
    val insert = userDbJdbcTemplate.update(
      insertQuery,
      user.userId,
      user.loginId,
      user.password,
      user.isLock,
      user.failedAttempts,
      user.lockTime,
      user.isDisabled
    )
    return user
  }

  override fun findByLoginId(loginId: String): UserRecord? {
    return try {
      userDbJdbcTemplate.queryForObject(
        "SELECT * FROM user WHERE login_id = ?",
        userRowMapper,
        loginId
      )
    } catch (ex: EmptyResultDataAccessException) {
      null
    }

  }

  override fun findByUserId(userId: String): UserRecord? {
    return try {
      userDbJdbcTemplate.queryForObject(
        "SELECT * FROM user WHERE user_id = ?",
        userRowMapper,
        userId
      )
    } catch (ex: EmptyResultDataAccessException) {
      null
    }
  }

  override fun findAll(): List<UserRecord> {
    val sql = "SELECT * FROM user"
    return userDbJdbcTemplate.query(sql) { rs, _ ->
      UserRecord(
        userId = rs.getString("user_id"),
        loginId = rs.getString("login_id"),
        password = rs.getString("password"),
        isLock = rs.getBoolean("is_lock"),
        failedAttempts = rs.getInt("failed_attempts"),
        lockTime = rs.getTimestamp("lock_time")?.toLocalDateTime(),
        isDisabled = rs.getBoolean("is_disabled"),
        createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
        updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
      )
    }
  }
}