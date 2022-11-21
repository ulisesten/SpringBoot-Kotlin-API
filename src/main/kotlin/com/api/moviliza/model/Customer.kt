package com.api.moviliza.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue

@Entity
class Customer(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = ""
)