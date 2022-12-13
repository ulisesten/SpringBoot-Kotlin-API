package com.api.moviliza.controller

import com.api.moviliza.model.Customer
import com.api.moviliza.persistence.CustomerRepository
import com.api.moviliza.utils.Jwt
import com.api.moviliza.utils.JwtRequest
import com.api.moviliza.utils.JwtResponse
import org.jose4j.json.internal.json_simple.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.xml.bind.JAXBElement.GlobalScope


//val creditRepository = CreditRepository

@RestController
@RequestMapping("/authenticate")
class LoginController(val repository: CustomerRepository, @Autowired val authenticationManager: AuthenticationManager) {

    val bcryptEncoder = BCryptPasswordEncoder()


    @PostMapping
    @Throws(java.lang.Exception::class)
    fun createAuthenticationToken(@RequestBody request: JwtRequest?): JSONObject? {
        val dbCustomer = repository.findByEmail(request!!.email.toString())

        val json = JSONObject()

        if(dbCustomer == null)
            return null

        if(dbCustomer.password == bcryptEncoder.encode(request.password))
            return null


            json.run {
                this["token"] = Jwt().generateToken(dbCustomer)
                this["email"] = dbCustomer.email
                this["userId"] = dbCustomer.userId
            }.apply {
                return json
            }


    }

    @Throws(Exception::class)
    private fun authenticate(request: JwtRequest): Customer? {
        var dbCustomer: Customer? = null
        try {
            dbCustomer = repository.findByEmail(request.email.toString())

            if((dbCustomer == null) || (dbCustomer.password != bcryptEncoder.encode(request.password))) {
                throw java.lang.Exception("INVALID_CREDENTIALS")
            }

            val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(dbCustomer.firstName, request.password))
                    ?: throw java.lang.Exception("INVALID_CREDENTIALS")

        } catch (e: DisabledException) {
            dbCustomer = null
        } catch (e: BadCredentialsException) {
            dbCustomer = null
        }
        return dbCustomer
    }

}