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
    @Column(name = "creditRequestId")
    var creditRequestId: Long = 0,
	var nombre: String = "",
    var valorVehiculo: Long = 0,
	var inicial:String = "",
	var plazo:String = "",
	var particular: Boolean = false,
	var trabajo: Boolean = false,
	var dni:String = "",
	var independiente: Boolean = false,
	var celular:String = "",
	var ingresos: Long = 0,
	var domicilio:String = "",
	var lugarDeTrabajo:String = "",
	var estadoCivil:String = "",
	
	@Column(name = "customer_id")
	var customerId: Long = 0,
    @OneToOne
    @PrimaryKeyJoinColumn(name="customer_id", referencedColumnName="credit_request_id")
    var customer: Customer? = null



)