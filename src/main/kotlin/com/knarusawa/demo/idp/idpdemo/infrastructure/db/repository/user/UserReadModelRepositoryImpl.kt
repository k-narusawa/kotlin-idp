package com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository.user

import com.knarusawa.demo.idp.idpdemo.configuration.db.UserDbJdbcTemplate
import com.knarusawa.demo.idp.idpdemo.domain.model.user.UserReadModel
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.record.UserRecord
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Repository

@Repository
class UserReadModelRepositoryImpl(
  private val userDbJdbcTemplate: UserDbJdbcTemplate
) : UserReadModelRepository {
  override fun findByLoginId(loginId: String): UserReadModel? {
    return try {
      userDbJdbcTemplate.queryForObject(
        "SELECT * FROM user WHERE login_id = ?",
        UserRepositoryImpl.userRowMapper,
        loginId
      )?.toUserReadModel()
    } catch (ex: EmptyResultDataAccessException) {
      null
    }

  }

  override fun findByUserId(userId: String): UserReadModel? {
    return try {
      userDbJdbcTemplate.queryForObject(
        "SELECT * FROM user WHERE user_id = ?",
        UserRepositoryImpl.userRowMapper,
        userId
      )?.toUserReadModel()
    } catch (ex: EmptyResultDataAccessException) {
      null
    }
  }

  override fun findAll(): List<UserReadModel> {
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
    return userRecords.map { it.toUserReadModel() }
  }
}