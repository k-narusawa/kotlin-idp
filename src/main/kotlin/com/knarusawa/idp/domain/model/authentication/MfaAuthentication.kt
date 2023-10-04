package com.knarusawa.idp.domain.model.authentication

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.CredentialsContainer
import java.util.*


class MfaAuthentication private constructor(
        val authentication: Authentication
) : AbstractAuthenticationToken(Collections.emptyList()) {

    companion object {
        fun of(authentication: Authentication) = MfaAuthentication(
                authentication = authentication
        )
    }

    override fun getPrincipal(): Any {
        return authentication.principal
    }

    override fun getCredentials(): Any {
        return authentication.credentials
    }

    override fun eraseCredentials() {
        if (authentication is CredentialsContainer) {
            (authentication as CredentialsContainer).eraseCredentials()
        }
    }

    override fun isAuthenticated(): Boolean {
        return false
    }

    fun getFirst(): Authentication {
        return authentication
    }
}