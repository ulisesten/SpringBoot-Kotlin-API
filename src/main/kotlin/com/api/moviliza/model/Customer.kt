package com.api.moviliza.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue

@Entity
class Customer(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var userid: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var availableLimit: Double = 0.0,
    var currentBalance: Double = 0.0,
    var paidBalance: Double = 0.0
)