package com.knarusawa.idp.infrastructure.middleware

import com.knarusawa.idp.config.Environments
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class DbColumnEncryptDecryptConverterTest{
    @RelaxedMockK
    private lateinit var environments: Environments

    @InjectMockKs
    private lateinit var converter: DbColumnEncryptDecryptConverter

    @BeforeEach
    fun setup(){
        MockKAnnotations.init(this)
        every { environments.cryptKey } returns "0123456789abcdef"
        every { environments.cryptSalt } returns "abcdef9876543210"
    }

    @Test
    @DisplayName("暗号化したカラムを複合できること")
    fun test1(){
        val rowData = "user@example.com"

        val encData = converter.convertToDatabaseColumn(rowData)
        val decData = converter.convertToEntityAttribute(encData)

        assertThat(decData).isEqualTo(rowData)
    }
}