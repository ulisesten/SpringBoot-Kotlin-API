package com.api.moviliza.utils

import com.api.moviliza.model.Customer
import org.jose4j.jwa.AlgorithmConstraints
import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims


class Jwt {
    val JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000
    val SECRET = "mysecret"
    var claims = JwtClaims()

    fun generateToken(customer: Customer): String {
        val claims = addClaims(customer);
		return doGenerateToken(claims, customer.firstName)
    }

    fun validateToken(): Boolean {
        return true
    }

    private fun addClaims(customer: Customer): JwtClaims {

        claims.setSubject("7560755e-f45d-4ebb-a098-b8971c02ebef") // set sub
        claims.setIssuedAtToNow() // set iat
        claims.setExpirationTimeMinutesInTheFuture(JWT_TOKEN_VALIDITY.toFloat()) // set exp
        //claims.setIssuer("https://moviliza.com") // set iss
        claims.setStringClaim("firstName", customer.firstName)
        claims.setStringClaim("lastName", customer.lastName)
        claims.setStringClaim("email", customer.email)
        claims.setClaim("email_verified", true)

        return claims
    }

    private fun doGenerateToken(claims: JwtClaims, subject: String): String {

        val jws = JsonWebSignature()
            jws.setAlgorithmConstraints(AlgorithmConstraints.NO_CONSTRAINTS)
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.NONE)
            jws.setPayload(claims.toJson())

        return jws.compactSerialization
    }
}