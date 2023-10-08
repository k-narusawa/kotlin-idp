package com.knarusawa.idp.domain.model

import com.knarusawa.idp.config.SecurityConfig
import com.knarusawa.idp.domain.value.LoginId
import com.knarusawa.idp.domain.value.Password
import com.knarusawa.idp.domain.value.Role
import com.knarusawa.idp.domain.value.UserId
import com.knarusawa.idp.infrastructure.adapter.db.record.UserRecord
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.time.LocalDateTime

class User private constructor(
        userId: UserId,
        loginId: LoginId,
        password: Password,
        roles: List<Role>,
        authorities: List<IdpGrantedAuthority>,
        isLock: Boolean,
        failedAttempts: Int,
        lockTime: LocalDateTime?,
        isDisabled: Boolean,
) : UserDetails, Serializable {
    val userId: UserId = userId
    var loginId: LoginId = loginId
        private set
    var password: Password = password
        private set
    var roles: List<Role> = roles
        private set
    var authorities: List<IdpGrantedAuthority> = authorities
        private set
    var isLock: Boolean = isLock
        private set
    var failedAttempts: Int = failedAttempts
        private set
    var lockTime: LocalDateTime? = lockTime
        private set
    var isDisabled: Boolean = isDisabled
        private set

    companion object {
        private const val MAX_LOGIN_ATTEMPTS = 5
        private const val AUTO_UNLOCK_DURATION_MIN = 30

        fun of(loginId: String, password: String, roles: List<Role>) =
                User(
                        userId = UserId.generate(),
                        loginId = LoginId(value = loginId),
                        password = Password(value = SecurityConfig().passwordEncoder().encode(password)),
                        authorities = listOf(),
                        roles = roles,
                        isLock = false,
                        failedAttempts = 0,
                        lockTime = null,
                        isDisabled = false,
                )

        fun from(userRecord: UserRecord) = User(
                userId = UserId(value = userRecord.userId),
                loginId = LoginId(value = userRecord.loginId),
                password = Password(value = userRecord.password),
                authorities = listOf(),
                roles = userRecord.roles.split(",").map { Role.fromString(it) },
                isLock = userRecord.isLock,
                failedAttempts = userRecord.failedAttempts,
                lockTime = userRecord.lockTime,
                isDisabled = userRecord.isDisabled,
        )
    }

    fun toRecord() = UserRecord(
            userId = this.userId.toString(),
            loginId = this.loginId.toString(),
            password = this.password.toString(),
            roles = this.roles.joinToString(","),
            isLock = this.isLock,
            failedAttempts = this.failedAttempts,
            lockTime = this.lockTime,
            isDisabled = this.isDisabled,
    )

    fun changeLoginId(loginId: String) {
        this.loginId = LoginId(value = loginId)
    }

    fun changePassword(password: String) {
        this.password = Password(value = SecurityConfig().passwordEncoder().encode(password))
    }

    fun authSuccess() {
        this.failedAttempts = 0
    }

    fun authFailed() {
        this.failedAttempts += 1
        if (!this.isLock && this.failedAttempts >= MAX_LOGIN_ATTEMPTS) {
            this.isLock = true
            this.lockTime = LocalDateTime.now()
        }
    }

    fun unlockByTimeElapsed() {
        val isEnabledUnLocked =
                this.lockTime?.let {
                    LocalDateTime.now().minusMinutes(AUTO_UNLOCK_DURATION_MIN.toLong()).isAfter(it)
                } ?: false

        if (this.isLock && isEnabledUnLocked) {
            this.isLock = false
            this.lockTime = null
        }
    }

    fun setAuthorities(authorities: List<IdpGrantedAuthority>) {
        this.authorities = authorities
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return (this.roles.map { SimpleGrantedAuthority("ROLE_${it.name}") } + this.authorities).toMutableList()
    }

    override fun getPassword(): String {
        return this.password.toString()
    }

    override fun getUsername(): String {
        return this.loginId.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return !this.isDisabled
    }

    override fun isAccountNonLocked(): Boolean {
        return !this.isLock
    }

    override fun isCredentialsNonExpired(): Boolean {
        return !this.isDisabled
    }

    override fun isEnabled(): Boolean {
        return !this.isDisabled
    }
}