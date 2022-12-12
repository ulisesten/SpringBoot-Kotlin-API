package com.api.moviliza.persistence

import com.api.moviliza.model.CreditRequest
import org.springframework.data.repository.CrudRepository

interface CreditRequestRepository : CrudRepository<CreditRequest, Long>