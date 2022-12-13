package com.api.moviliza.utils

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtRequestFilter: OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String? = null
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            val jwtTokenUtil = Jwt().validateToken(jwtToken)
            try {
                username = jwtTokenUtil!!["firstName"].toString()
                validateToken(Jwt(), username, chain, request, response)
            } catch (e: IllegalArgumentException) {
                //Log.d("Unable to get JWT Token", e)
            } catch (e: ExpiredJwtException) {
                //Log.d("JWT Token has expired", e)
            }
        } else {
            chain.doFilter(request, response)
        }
    }

    @Throws(ServletException::class, IOException::class)
    private fun validateToken(jwtTokenUtil: Jwt, username: String?, chain: FilterChain, request: HttpServletRequest,
                              response: HttpServletResponse) {

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            //val userDetails: UserDetails = jwtUserDetailsService.loadUserByUsername(username)
            val userDetails = jwtTokenUtil.validateToken("")
            // if token is valid configure Spring Security to manually set
            // authentication
            if ( userDetails != null) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, null)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}