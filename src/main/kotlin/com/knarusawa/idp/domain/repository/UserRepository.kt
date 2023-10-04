package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.User


interface UserRepository {
    fun save(user: User)
    fun findByUserId(userId: String): User?
    fun findByLoginId(loginId: String): User?
    fun deleteByUserId(userId: String)
}