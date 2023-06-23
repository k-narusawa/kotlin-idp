package com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository

import com.knarusawa.demo.idp.idpdemo.configuration.db.UserDbJdbcTemplate
import com.knarusawa.demo.idp.idpdemo.domain.repository.RoleRepository
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.RoleEntity
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class RoleRepositoryImpl(
  private val userDbJdbcTemplate: UserDbJdbcTemplate
) : RoleRepository {
  companion object {
    val roleRowMapper = RowMapper { rs: ResultSet, _: Int ->
      RoleEntity(
        roleId = rs.getInt("role_id"),
        userId = rs.getString("user_id"),
        role = rs.getString("role"),
        createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
        updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
      )
    }
  }

  override fun save(role: RoleEntity): RoleEntity {
    val insertQuery =
      "INSERT INTO role (user_id, role) VALUES (?, ?)"
    val insert = userDbJdbcTemplate.update(
      insertQuery,
      role.userId,
      role.role,
    )
    return role
  }

  override fun findByUserId(userId: String): List<RoleEntity> {
    return userDbJdbcTemplate.query("SELECT * FROM role WHERE user_id = ?", roleRowMapper, userId)
  }
}