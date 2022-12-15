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


//val creditRepository = CreditRepository

@RestController
@RequestMapping("/customers")
class CustomerController(val repository: CustomerRepository, @Autowired val authenticationManager: AuthenticationManager) {

    val bcryptEncoder = BCryptPasswordEncoder()

    @GetMapping
    fun findAll(): Iterable<Customer> {
        return repository.findAll()
    }

    @GetMapping("/{id}")
    fun checkCustomer(@PathVariable("id") id: Long): JSONObject {
        val json = JSONObject()
        return try {
            val dbOptional = repository.findById(id)
            val dbCustomer = dbOptional.get()



            json["token"] = Jwt().generateToken(dbCustomer)
            json["email"] = dbCustomer.email
            json["userId"] = dbCustomer.userId
            json["creditActive"] = dbCustomer.creditActive
            json["creditId"] = dbCustomer.creditId

            json
        } catch (e: Exception){
            json["message"] = "customer not found"
            json
        }
    }



    /*@GetMapping("/{email}")
    fun checkCustomer(@PathVariable("email") email: String): JSONObject {
        val dbCustomer = repository.findByEmail(email)

        val json = JSONObject()

        if(dbCustomer != null) {
            json["token"] = Jwt().generateToken(dbCustomer)
            json["email"] = dbCustomer.email
            json["userId"] = dbCustomer.userId
        }

        return json
    }*/


    

    @PutMapping("/{id}")
    fun updateCustomer(@PathVariable id: Long, @RequestBody customer: Customer) {
        assert(customer.userId == id)
        repository.save(customer)
    }


    @DeleteMapping("/{id}")
    fun removeCustomer(@PathVariable customer: Customer)
            = repository.delete(customer)
    
    fun encode(password: String):String {

        return bcryptEncoder.encode(password)
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