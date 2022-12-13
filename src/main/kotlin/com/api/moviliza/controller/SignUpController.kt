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

@RestController
@RequestMapping("/register")
class SignUpController(val repository: CustomerRepository) {

    val bcryptEncoder = BCryptPasswordEncoder()

    @PostMapping
    fun addCustomer(@RequestBody customer: Customer): JSONObject{
        customer.password = bcryptEncoder.encode(customer.password)
        repository.save(customer)

        val json = JSONObject()

        json["token"] = Jwt().generateToken(customer)
        json["email"] = customer.email
        json["userId"] = customer.userId

        return json
    }
}