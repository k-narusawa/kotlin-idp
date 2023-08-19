package com.knarusawa.demo.idp.idpdemo.infrastructure.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class RequestFilter : OncePerRequestFilter() {
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    logger.info("request User-Agent: " + request.getHeader("User-Agent"));
    val start = System.currentTimeMillis()
    try {
      filterChain.doFilter(request, response)
    } finally {
      val end = System.currentTimeMillis()
      logger.info("METHOD: ${request.method}, REQUEST_URL: ${request.requestURI}, REQUEST_TIME: ${end - start}ms")
    }
  }
}