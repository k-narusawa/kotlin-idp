package com.knarusawa.idp.infrastructure.adapter.db.repository

import com.knarusawa.idp.config.db.UserDbJdbcTemplate
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.stereotype.Repository

@Repository
class ExtendedRegisteredClientRepository(
        private val userDbJdbcTemplate: UserDbJdbcTemplate
) : JdbcRegisteredClientRepository(userDbJdbcTemplate) {
    fun findAll(): List<RegisteredClient> {
        val sql = "SELECT * FROM oauth2_registered_client"

        return userDbJdbcTemplate.query(sql) { rs, _ ->
            RegisteredClient.withId(rs.getString("id"))
                    .clientId(rs.getString("client_id"))
                    .clientSecret(rs.getString("client_secret"))
                    .clientAuthenticationMethods {
                        it.addAll(
                                rs.getString("client_authentication_methods").split(",").map {
                                    ClientAuthenticationMethod(it)
                                })
                    }
                    .authorizationGrantTypes {
                        it.addAll(
                                rs.getString("authorization_grant_types").split(",").map {
                                    AuthorizationGrantType(it)
                                })
                    }
                    .redirectUris {
                        it.addAll(
                                rs.getString("redirect_uris").split(",").map {
                                    it
                                })
                    }
                    .scopes {
                        it.addAll(
                                rs.getString("scopes").split(",").map {
                                    it
                                })
                    }
                    .build()
        }
    }

    fun deleteClient(clientId: String) {
        val params = mapOf("clientId" to clientId)
        userDbJdbcTemplate.update(
                "DELETE FROM oauth2_registered_client WHERE client_id = :clientId",
                params
        )
    }
}