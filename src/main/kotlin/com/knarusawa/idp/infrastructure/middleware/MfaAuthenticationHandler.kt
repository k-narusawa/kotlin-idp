package com.knarusawa.idp.infrastructure.middleware

import com.knarusawa.idp.domain.model.MfaAuthentication
import com.knarusawa.idp.domain.value.AuthorityRole
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler


class MfaAuthenticationHandler(
        nextAuthUrl: String,
) : AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private val successHandler = SimpleUrlAuthenticationSuccessHandler(nextAuthUrl)
    private val loginCompleteSuccessHandler = SavedRequestAwareAuthenticationSuccessHandler()

    init {
        successHandler.setAlwaysUseDefaultTargetUrl(true)
    }

    override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication
    ) {
        val isUsingMfa = authentication.authorities?.any {
            it.authority == AuthorityRole.MFA_APP.toString() ||
                    it.authority == AuthorityRole.MFA_MAIL.toString() ||
                    it.authority == AuthorityRole.MFA_SMS.toString()
        } ?: false

        if (isUsingMfa) {
            saveMfaAuthentication(request!!, response!!, authentication)
        } else {
            loginCompleteSuccessHandler.onAuthenticationSuccess(request, response, authentication)
        }
    }

    private fun saveMfaAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authentication: Authentication
    ) {
        SecurityContextHolder.getContext().authentication = MfaAuthentication.of(authentication)
        successHandler.onAuthenticationSuccess(request, response, authentication)
    }

    override fun onAuthenticationFailure(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            exception: AuthenticationException?
    ) {
        val anonymous: Authentication = AnonymousAuthenticationToken(
                "key",
                "anonymousUser",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
        )
        saveMfaAuthentication(request!!, response!!, MfaAuthentication.of(anonymous))
    }
}