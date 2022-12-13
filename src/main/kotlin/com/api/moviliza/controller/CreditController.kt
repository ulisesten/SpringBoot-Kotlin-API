package com.api.moviliza.controller

import com.api.moviliza.model.Customer
import com.api.moviliza.model.CreditInfo
import com.api.moviliza.persistence.CreditRepository
import com.api.moviliza.persistence.CustomerRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/credits")
class CreditController(val repository: CreditRepository, @Autowired val customerRepository: CustomerRepository) {

    //@Autowired
    //private lateinit var customerRepository: CustomerRepository

    @GetMapping
    fun findAll() = repository.findAll()

    @PostMapping
    fun addCredit(@RequestBody credit: CreditInfo) {

        val customer = customerRepository.findById(credit.customerId).get()

        credit.customer = customer
        credit.customerId = customer.userId

        val savedCredit = repository.save(credit)

        customer.creditId = savedCredit.creditInfoId
        customer.creditInfo = savedCredit
        customer.creditActive = true

        customerRepository.save(customer)

    }

    @PutMapping("/{id}")
    fun updateCredit(@PathVariable id: Long, @RequestBody credit: CreditInfo) {
        assert(credit.creditInfoId == id)
        repository.save(credit)
    }

    @DeleteMapping("/{id}")
    fun removeCustomer(@PathVariable credit: CreditInfo)
            = repository.delete(credit)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long)
            = repository.findById(id)
}