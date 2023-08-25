package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UserServiceTest {
  companion object {
    val DUMMY_USER = UserReadModel(
      userId = UserId.generate(),
      loginId = LoginId(value = "test@example.com"),
      password = Password(value = null),
      roles = listOf(),
      isLock = false,
      failedAttempts = null,
      lockTime = null,
      isDisabled = false,
      createdAt = LocalDateTime.now(),
      updatedAt = LocalDateTime.now(),
    )
  }

  @RelaxedMockK
  private lateinit var userReadModelRepository: UserReadModelRepository

  @InjectMockKs
  private lateinit var userService: UserService

  @BeforeEach
  fun setUp() {
    MockKAnnotations.init(this)
  }

  @Nested
  inner class ログインIDの存在チェック {
    @Test
    fun `ログインIDが存在しない場合にfalseを返すこと`() {
      every { userReadModelRepository.findByLoginId(any()) } returns null

      val actual = userService.isExistsLoginId(loginId = LoginId("test@example.com"))
      assertThat(actual).isFalse()
    }

    @Test
    fun `ログインIDが使用済みの場合にtrueを返すこと`() {
      every { userReadModelRepository.findByLoginId(any()) } returns DUMMY_USER

      val actual = userService.isExistsLoginId(loginId = LoginId("test@example.com"))
      assertThat(actual).isTrue()
    }
  }
}
