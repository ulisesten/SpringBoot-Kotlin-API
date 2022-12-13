package com.api.moviliza.controller

import com.api.moviliza.utils.JwtRequestFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfiguration: WebSecurityConfigurerAdapter() {

  @Throws(Exception::class)
  override fun configure(httpSecurity: HttpSecurity) {
    httpSecurity
    .csrf()
    .disable()
    .addFilterBefore(JwtRequestFilter(), UsernamePasswordAuthenticationFilter::class.java)
    .authorizeRequests()
    .antMatchers("/*" + "/**" )
    .permitAll()
    .antMatchers(HttpMethod.POST, "/credits").access("hasAuthority('JWT_USER')")
    .anyRequest().authenticated().and()
    .exceptionHandling().authenticationEntryPoint(JwtAuthenticationEntryPoint()).and()
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  @Bean
  @Throws(java.lang.Exception::class)
  override fun authenticationManagerBean(): AuthenticationManager? {
    return super.authenticationManagerBean()
  }
}

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
  @Throws(IOException::class)
  override fun commence(request: HttpServletRequest?, response: HttpServletResponse,
                        authException: AuthenticationException) {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.message)
  }
}