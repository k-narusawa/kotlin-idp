package com.knarusawa.idp.infrastructure.adapter.db.repository

import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.model.userMfa.MfaType
import com.knarusawa.idp.domain.model.userMfa.UserMfa
import com.knarusawa.idp.infrastructure.adapter.db.record.UserMfaRecord
import com.knarusawa.idp.infrastructure.adapter.db.repository.userMfa.UserMfaRecordRepository
import com.warrenstrange.googleauth.ICredentialRepository
import org.springframework.stereotype.Component


@Component
class CredentialRepository(
  private val userMfaRecordRepository: UserMfaRecordRepository
) : ICredentialRepository {
  override fun getSecretKey(userName: String?): String {
    if (userName == null)
      throw IllegalArgumentException("UserId is Not Null.")

    val record = userMfaRecordRepository.findByUserId(userId = userName)

    return record.secretKey ?: throw IdpAppException(
      errorCode = ErrorCode.INTERNAL_SERVER_ERROR,
      logMessage = "SecretKeyの値が未設定"
    )
  }

  override fun saveUserCredentials(
    userName: String?,
    secretKey: String?,
    validationCode: Int,
    scratchCodes: MutableList<Int>?
  ) {
    val userMfa = UserMfa.of(
      userId = userName?.let { UserId(value = it) }
        ?: throw IllegalArgumentException("UserId is Not Null."),
      type = MfaType.APP,
      secretKey = secretKey,
      validationCode = validationCode,
      scratchCodes = scratchCodes
    )

    userMfaRecordRepository.save(UserMfaRecord.from(userMfa))
  }
}