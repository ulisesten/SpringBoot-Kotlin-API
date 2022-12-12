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
class CreditRequest (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "creditInfoId")
    var creditRequestId: Long = 0,
    var valor_vehiculo: Long = 0,
	var inicial:String = "",
	var plazo:String = "",
	var particular: Boolean = false,
	var trabajo: Boolean = false,
	var dni:String = "",
	var independiente: Boolean = false,
	var celular:String = "",
	var ingresos: Long = 0,
	var direccion:String = "",
	var lugar_de_trabajo:String = "",
	var estado_civil:String = "",

    @OneToOne
    @PrimaryKeyJoinColumn(name="customer_id", referencedColumnName="credit_id")
    var customer: Customer? = null
    
) {
    @Column(name = "customer_id")
    var customerId: Long = 0
}