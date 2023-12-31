package com.knarusawa.idp.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.knarusawa.idp.domain.value.AuthorityRole
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable

class IdpGrantedAuthority private constructor(
        @JsonProperty("authorityRole")
        private val authorityRole: AuthorityRole? = null
) : GrantedAuthority, Serializable {

    companion object {
        fun useMfaApp(): IdpGrantedAuthority {
            return IdpGrantedAuthority(authorityRole = AuthorityRole.MFA_APP)
        }

        fun useMfaMail(): IdpGrantedAuthority {
            return IdpGrantedAuthority(authorityRole = AuthorityRole.MFA_MAIL)
        }

        fun useMfaSms(): IdpGrantedAuthority {
            return IdpGrantedAuthority(authorityRole = AuthorityRole.MFA_SMS)
        }

        fun usePassword(): IdpGrantedAuthority {
            return IdpGrantedAuthority(authorityRole = AuthorityRole.PASSWORD)
        }
    }

    override fun getAuthority(): String {
        return this.authorityRole?.toString() ?: ""
    }

    override fun toString(): String {
        return this.authorityRole?.toString() ?: ""
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonSubTypes
    abstract class IdpGrantedAuthorityMixin {
        @JsonProperty("authorityRole")
        private lateinit var authorityRole: AuthorityRole
    }
}