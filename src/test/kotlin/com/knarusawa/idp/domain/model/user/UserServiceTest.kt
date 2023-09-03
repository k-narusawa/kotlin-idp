package com.knarusawa.idp.domain.model.user

import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.infrastructure.db.record.UserRecord
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UserServiceTest {
  companion object {
    val DUMMY_USER_RECORD = UserRecord(
      loginId = "test",
      password = "password",
    )
  }

  @RelaxedMockK
  private lateinit var userRepository: UserRepository

  @InjectMockKs
  private lateinit var userService: UserService

  @BeforeEach
  fun setUp() {
    MockKAnnotations.init(this)
  }

  @Nested
  @DisplayName("ログインIDの存在チェックテスト")
  inner class IsExistsLoginId {
    @Test
    @DisplayName("ログインIDが存在しない場合にfalseを返すこと")
    fun test1() {
      every { userRepository.findByLoginId(any()) } returns null

      val actual = userService.isExistsLoginId(loginId = LoginId("test"))
      assertThat(actual).isFalse()
    }

    @Test
    @DisplayName("ログインIDが使用済みの場合にtrueを返すこと")
    fun test2() {
      every { userRepository.findByLoginId(any()) } returns DUMMY_USER_RECORD

      val actual = userService.isExistsLoginId(loginId = LoginId("test"))
      assertThat(actual).isTrue()
    }
  }
}
