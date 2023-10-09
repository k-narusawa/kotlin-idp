package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.LoginIdUpdateDate
import com.knarusawa.idp.domain.value.LoginIdUpdateKey
import com.knarusawa.idp.domain.value.LoginIdUpdateValue

interface LoginIdUpdateDateRepository {
    fun save(data: LoginIdUpdateDate)
    fun findByKey(key: String): LoginIdUpdateValue?
    fun deleteByKey(key: LoginIdUpdateKey)
}