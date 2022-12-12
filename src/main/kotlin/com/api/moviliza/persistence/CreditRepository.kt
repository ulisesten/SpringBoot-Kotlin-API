package com.api.moviliza.persistence

import com.api.moviliza.model.CreditInfo
import org.springframework.data.repository.CrudRepository

interface CreditRepository : CrudRepository<CreditInfo, Long>