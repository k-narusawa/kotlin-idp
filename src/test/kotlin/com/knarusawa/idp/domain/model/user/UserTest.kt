package com.knarusawa.idp.domain.model.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

class UserTest {
    companion object {
        const val LOGIN_ID = "test@example.com"
        const val PASSWORD = "test"
    }

    @Nested
    @DisplayName("認証前のアカウントロック解除確認")
    inner class UnlockByTimeElapsed {
        @Test
        @DisplayName("30分以上前にアカウントロックされている場合にアカウントロックが解除されること")
        fun test1() {
            val user = User.of(
                    loginId = LOGIN_ID,
                    password = PASSWORD,
                    roles = listOf(Role.USER)
            )
            ReflectionTestUtils.setField(user, "isLock", true)
            ReflectionTestUtils.setField(user, "lockTime", LocalDateTime.now().minusHours(1))
            ReflectionTestUtils.setField(user, "failedAttempts", 5)

            user.unlockByTimeElapsed()

            assertThat(user.isLock).isFalse()
            assertThat(user.lockTime).isNull()
        }

        @Test
        @DisplayName("30分以内にアカウントロックされている場合にアカウントロックが解除されないこと")
        fun test2() {
            val user = User.of(
                    loginId = LOGIN_ID,
                    password = PASSWORD,
                    roles = listOf(Role.USER)
            )
            ReflectionTestUtils.setField(user, "isLock", true)
            ReflectionTestUtils.setField(user, "lockTime", LocalDateTime.now().minusMinutes(29))
            ReflectionTestUtils.setField(user, "failedAttempts", 5)

            user.unlockByTimeElapsed()

            assertThat(user.isLock).isTrue()
            assertThat(user.lockTime).isNotNull()
        }
    }

    @Nested
    @DisplayName("認証成功時")
    inner class AuthSuccess {
        @Test
        @DisplayName("認証失敗回数がリセットされること")
        fun test1() {
            val user = User.of(
                    loginId = LOGIN_ID,
                    password = PASSWORD,
                    roles = listOf(Role.USER)
            )

            ReflectionTestUtils.setField(user, "failedAttempts", 5)

            user.authSuccess()

            assertThat(user.failedAttempts).isEqualTo(0)
        }
    }

    @Nested
    @DisplayName("認証失敗時")
    inner class AuthFailed {
        @Test
        @DisplayName("初回の認証失敗時に失敗回数がカウントされること")
        fun test1() {
            val user = User.of(
                    loginId = LOGIN_ID,
                    password = PASSWORD,
                    roles = listOf(Role.USER)
            )

            user.authFailed()

            assertThat(user.failedAttempts).isEqualTo(1)
        }

        @Test
        @DisplayName("アカウントロック対象回数の認証失敗時に失敗回数がカウントされてアカウントがロックされること")
        fun test2() {
            val user = User.of(
                    loginId = LOGIN_ID,
                    password = PASSWORD,
                    roles = listOf(Role.USER)
            )

            ReflectionTestUtils.setField(user, "failedAttempts", 4)
            user.authFailed()

            assertThat(user.isLock).isTrue()
            assertThat(user.failedAttempts).isEqualTo(5)
            assertThat(user.lockTime).isNotNull()
        }
    }
}