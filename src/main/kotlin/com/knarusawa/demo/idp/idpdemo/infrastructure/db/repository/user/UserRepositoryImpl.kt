package com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository.user

import com.knarusawa.demo.idp.idpdemo.configuration.db.UserDbJdbcTemplate
import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserRepository
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.record.UserRecord
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
        roles = rs.getString("roles").split(","),
        isLock = rs.getBoolean("is_lock"),
        failedAttempts = rs.getInt("failed_attempts"),
        lockTime = rs.getTimestamp("lock_time")?.toLocalDateTime(),
        isDisabled = rs.getBoolean("is_disabled"),
        createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
        updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
      )
    }
  }

  override fun save(user: User): User {
    val insertQuery = StringBuilder()
      .append("INSERT INTO user")
      .append("(user_id, login_id, password, roles, is_lock, failed_attempts, lock_time, is_disabled)")
      .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
      .toString()
    val insertNum = userDbJdbcTemplate.update(
      insertQuery,
      user.userId.toString(),
      user.loginId.toString(),
      user.password.toString(),
      user.roles.joinToString(","),
      user.isLock,
      user.failedAttempts,
      user.lockTime,
      user.isDisabled
    )
    if (insertNum != 1)
      throw AppException(
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR,
        logMessage = "Internal Server Error"
      )
    return user
  }

  override fun update(user: User): User {
    val updateQuery = StringBuilder()
      .append("UPDATE user SET ")
      .append("login_id = ?, ")
      .append("password = ?, ")
      .append("roles = ?, ")
      .append("is_lock = ?, ")
      .append("failed_attempts = ?, ")
      .append("lock_time = ?, ")
      .append("is_disabled = ? ")
      .append("WHERE user_id = ?")
      .toString()
    val updateNum = userDbJdbcTemplate.update(
      updateQuery,
      user.loginId.toString(),
      user.password.toString(),
      user.roles.joinToString(","),
      user.isLock,
      user.failedAttempts,
      user.lockTime,
      user.isDisabled,
      user.userId.toString(),
    )
    if (updateNum != 1)
      throw AppException(
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR,
        logMessage = "Internal Server Error"
      )
    return user
  }

  override fun findByUserId(userId: String): User? {
    return try {
      userDbJdbcTemplate.queryForObject(
        "SELECT * FROM user WHERE user_id = ?",
        userRowMapper,
        userId
      )?.toUser()
    } catch (ex: EmptyResultDataAccessException) {
      null
    }
  }
}