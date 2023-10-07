package com.knarusawa.idp.infrastructure.middleware

import com.knarusawa.idp.config.Environments
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Converter
class DbColumnEncryptDecryptConverter(
        private val environments: Environments
): AttributeConverter<String, String> {
    private val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    override fun convertToDatabaseColumn(attribute: String?): String? {
        if (attribute == null) return null

        val secretKeySpec = SecretKeySpec(environments.cryptKey.toByteArray(), "AES")
        val ivParameterSpec = IvParameterSpec(environments.cryptSalt.toByteArray())

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

        val encrypted = cipher.doFinal(attribute.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    override fun convertToEntityAttribute(dbData: String?): String? {
        if (dbData == null) return null

        val secretKeySpec = SecretKeySpec(environments.cryptKey.toByteArray(), "AES")
        val ivParameterSpec = IvParameterSpec(environments.cryptSalt.toByteArray())

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

        val decrypted = cipher.doFinal(Base64.getDecoder().decode(dbData))
        return String(decrypted)
    }
}