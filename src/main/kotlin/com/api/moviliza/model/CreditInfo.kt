package com.api.moviliza.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.PrimaryKeyJoinColumn

import javax.persistence.MapsId
import javax.persistence.JoinColumn

import com.api.moviliza.model.Customer

@Entity
class CreditInfo(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "creditInfoId")
    var creditInfoId: Long = 0,
    var availableLimit: Double = 0.0,
    var currentBalance: Double = 0.0,
    var paidBalance: Double = 0.0,
    @OneToOne
    @PrimaryKeyJoinColumn(name="customer_id", referencedColumnName="credit_id")
    var customer: Customer? = null,
    @Column(name = "customer_id")
    var customerId: Long = 0
)