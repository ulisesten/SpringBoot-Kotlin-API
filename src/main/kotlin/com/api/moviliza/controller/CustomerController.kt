package com.api.moviliza.controller

import com.api.moviliza.model.Customer
import com.api.moviliza.model.CreditInfo
import com.api.moviliza.persistence.CustomerRepository
import com.api.moviliza.persistence.CreditRepository
import org.jose4j.json.internal.json_simple.JSONObject
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.stereotype.Component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

//val creditRepository = CreditRepository

@RestController
@RequestMapping("/customers")
class CustomerController(val repository: CustomerRepository) {

    @GetMapping
    fun findAll() = repository.findAll()

    @PostMapping
    fun addCustomer(@RequestBody customer: Customer): JSONObject{
        customer.password = this.encode(customer.password)
        repository.save(customer)

        val json = JSONObject()
        json["token"] = "a9d7fa9s8df"
        return json
    }

    @GetMapping("/{email}")
    fun checkCustomer(@PathVariable("email") email: String): JSONObject {
        val dbCustomer = repository.findByEmail(email)

        val json = JSONObject()
        json["token"] = "a9d7fa9s8df"
        if(dbCustomer != null)
            json["email"] = dbCustomer.email

        return json
    }
    

    @PutMapping("/{id}")
    fun updateCustomer(@PathVariable id: Long, @RequestBody customer: Customer) {
        assert(customer.userId == id)
        repository.save(customer)
    }


    @DeleteMapping("/{id}")
    fun removeCustomer(@PathVariable customer: Customer)
            = repository.delete(customer)

    /*@GetMapping("/{id}")
    fun getById(@PathVariable id: Long)
            = repository.findById(id)

     */

    
    fun encode(password: String):String {
        val bcryptEncoder = BCryptPasswordEncoder()
        return bcryptEncoder.encode(password)
    }
    
}