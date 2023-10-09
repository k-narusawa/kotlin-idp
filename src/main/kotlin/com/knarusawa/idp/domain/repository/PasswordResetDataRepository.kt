package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.PasswordResetData
import com.knarusawa.idp.domain.value.PasswordResetKey
import com.knarusawa.idp.domain.value.PasswordResetValue

interface PasswordResetDataRepository {
    fun save(data: PasswordResetData)
    fun findByKey(key: String): PasswordResetValue?
    fun deleteByKey(key: PasswordResetKey)
}