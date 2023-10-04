package com.knarusawa.idp.domain.model.authority

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority

class IdpGrantedAuthority private constructor(
        @JsonProperty("authorityRole")
        private val authorityRole: AuthorityRole? = null
) : GrantedAuthority {

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
}