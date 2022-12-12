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
//import javax.persistence.PrimaryKeyJoinColumn
//import com.api.moviliza.model.CreditInfo

@Entity
class Provider(
    @Id
    var providerId: Long = 0,
    var name: String = "",
    var logo: String = "",
    var description: String = "",
    var companies: Array<Company> = arrayOf()
)