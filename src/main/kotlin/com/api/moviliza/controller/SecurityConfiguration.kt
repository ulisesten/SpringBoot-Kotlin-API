package com.api.moviliza.controller

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfiguration: WebSecurityConfigurerAdapter() {

  @Throws(Exception::class)
  override fun configure(httpSecurity: HttpSecurity) {
    httpSecurity
    .csrf()
    .disable()
    //.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    .authorizeRequests()
    .antMatchers("/customers" + "/**" )
    .permitAll()
    .anyRequest()
    .authenticated()
    .and()
    .httpBasic()
  }


}