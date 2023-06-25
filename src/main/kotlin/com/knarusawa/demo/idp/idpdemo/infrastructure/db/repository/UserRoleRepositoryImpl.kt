package com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository

import com.knarusawa.demo.idp.idpdemo.configuration.db.UserDbJdbcTemplate
import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.UserRole
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRoleRepository
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.record.UserRoleRecord
import java.sql.ResultSet
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class UserRoleRepositoryImpl(
  private val userDbJdbcTemplate: UserDbJdbcTemplate
) : UserRoleRepository {
  companion object {
    val roleRowMapper = RowMapper { rs: ResultSet, _: Int ->
      UserRoleRecord(
        roleId = rs.getInt("role_id"),
        userId = rs.getString("user_id"),
        role = rs.getString("role"),
        createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
        updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
      )
    }
  }

  override fun save(role: UserRole): UserRole {
    val insertQuery =
      "INSERT INTO role (user_id, role) VALUES (?, ?)"
    val insert = userDbJdbcTemplate.update(
      insertQuery,
      role.userId,
      role.role,
    )
    return role
  }

  override fun findByUserId(userId: String): List<UserRole> {
    return userDbJdbcTemplate.query("SELECT * FROM role WHERE user_id = ?", roleRowMapper, userId)
      .map { it.toUserRole() }
  }
}