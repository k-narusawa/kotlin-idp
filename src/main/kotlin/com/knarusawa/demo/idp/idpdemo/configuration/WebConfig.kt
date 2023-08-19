package com.knarusawa.demo.idp.idpdemo.configuration

import com.knarusawa.demo.idp.idpdemo.infrastructure.filter.RequestFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
  @Bean
  fun filterRegister(): FilterRegistrationBean<RequestFilter> {
    val registrationBean = FilterRegistrationBean<RequestFilter>()
    registrationBean.filter = RequestFilter()
    registrationBean.addUrlPatterns("/*")
    return registrationBean
  }
}