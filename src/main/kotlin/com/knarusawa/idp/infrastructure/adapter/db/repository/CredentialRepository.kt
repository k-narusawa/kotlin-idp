package com.knarusawa.idp.infrastructure.adapter.db.repository

import com.warrenstrange.googleauth.ICredentialRepository
import org.springframework.stereotype.Component


@Component
class CredentialRepository : ICredentialRepository {
  override fun getSecretKey(userName: String?): String {
    TODO("Not yet implemented")
  }

  override fun saveUserCredentials(
    userName: String?,
    secretKey: String?,
    validationCode: Int,
    scratchCodes: MutableList<Int>?
  ) {
    TODO("Not yet implemented")
  }
}