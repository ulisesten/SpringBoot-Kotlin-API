package com.api.moviliza.persistence

import com.api.moviliza.model.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, Long>{
    fun findByEmail(email: String): Customer?
    //override fun findById(id: Long): Customer?
}