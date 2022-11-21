package com.api.moviliza.persistence

import com.api.moviliza.model.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Long>