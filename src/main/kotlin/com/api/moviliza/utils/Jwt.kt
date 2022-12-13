package com.api.moviliza.utils

import com.api.moviliza.model.Customer
import org.jose4j.jwa.AlgorithmConstraints
import org.jose4j.jws.AlgorithmIdentifiers
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.InvalidJwtException
import org.jose4j.jwt.consumer.JwtConsumerBuilder


class Jwt {
    val JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000
    val SECRET = "mysecret"
    var claims = JwtClaims()

    fun generateToken(customer: Customer): String {
        val claims = addClaims(customer);
		return doGenerateToken(claims, customer.firstName)
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

    fun validateToken(jwt: String): Map<String?, Any?>? {
        lateinit var jwtClaims: JwtClaims

        val jwtConsumer = JwtConsumerBuilder() // required for NONE alg
                .setJwsAlgorithmConstraints(AlgorithmConstraints.NO_CONSTRAINTS) // disable signature requirement
                .setDisableRequireSignature() // require the JWT to have iat field
                .setRequireIssuedAt() // require the JWT to have exp field
                .setRequireExpirationTime() // expect the iss to be https://codecurated.com
                //.setExpectedIssuer("https://moviliza.com")
                .build()

        try {
            val jwtContext = jwtConsumer.process(jwt)
            val jws = jwtContext.joseObjects[0] as JsonWebSignature
            jwtClaims = jwtContext.jwtClaims

        } catch (e: InvalidJwtException){
            return null
        }

        return jwtClaims.claimsMap
    }
}