package com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository

import com.knarusawa.demo.idp.idpdemo.configuration.db.UserDbJdbcTemplate
import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.record.UserRecord
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet


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
    val insertQuery =
        "INSERT INTO user (user_id, login_id, password, roles, is_lock, failed_attempts, lock_time, is_disabled) VALUES (?, ?, ?, ?, ?, ?, ?)"
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

  override fun findByLoginId(loginId: String): User? {
    return try {
      userDbJdbcTemplate.queryForObject(
          "SELECT * FROM user WHERE login_id = ?",
          userRowMapper,
          loginId
      )?.toUser()
    } catch (ex: EmptyResultDataAccessException) {
      null
    }

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

  override fun findAll(): List<User> {
    val sql = "SELECT * FROM user"
    val userRecords = userDbJdbcTemplate.query(sql) { rs, _ ->
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
    return userRecords.map { it.toUser() }
  }
}