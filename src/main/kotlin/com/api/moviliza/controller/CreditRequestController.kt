package com.api.moviliza.controller

import com.api.moviliza.model.CreditRequest
import com.api.moviliza.persistence.CreditRequestRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/credit_requests")
class CreditRequestController(val repository: CreditRequestRepository) {

    @GetMapping
    fun findAll() = repository.findAll()

    @PostMapping
    fun addCreditRequest(@RequestBody creditRequest: CreditRequest)
            = repository.save(creditRequest)
    

    @PutMapping("/{id}")
    fun updateCreditRequest(@PathVariable id: Long, @RequestBody creditRequest: CreditRequest) {
        assert(creditRequest.creditRequestId == id)
        repository.save(creditRequest)
    }

    @DeleteMapping("/{id}")
    fun removeCreditRequest(@PathVariable creditRequest: CreditRequest)
            = repository.delete(creditRequest)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long)
            = repository.findById(id)
}