package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.OneTimePassword
import com.knarusawa.idp.domain.value.UserId

interface OnetimePasswordRepository {
    suspend fun save(oneTimePassword: OneTimePassword)

    suspend fun findByUserId(userId: UserId): OneTimePassword?

    suspend fun deleteByUserId(userId: UserId)
}