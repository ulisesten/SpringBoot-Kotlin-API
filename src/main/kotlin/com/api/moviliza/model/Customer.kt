package com.api.moviliza.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue
import javax.persistence.CascadeType
import javax.persistence.OneToOne
import javax.persistence.JoinColumn
import javax.persistence.Table
import javax.persistence.Column

import javax.persistence.PrimaryKeyJoinColumn

import com.api.moviliza.model.CreditInfo

@Entity
class Customer(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    var userId: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var creditActive: Boolean = false,
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "creditInfoId")
    var creditInfo: CreditInfo? = null,
    @Column(name = "credit_id")
    var creditId: Long = 0
)