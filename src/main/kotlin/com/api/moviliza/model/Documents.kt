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
class Documents (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "creditInfoId")
    var documentsId: Long = 0,
    var dni: String = "",
	var recibo_luz_agua: String = "",
	var boleta_pago_1: String = "",
	var boleta_pago_2: String = "",
	var boleta_pago_3: String = "",
	var justificacion_patrimonio: String = "",
	var ficha_ruc_rus: String = "",
	var pdt_pagado_1: String = "",
	var pdt_pagado_2: String = "",
	var pdt_pagado_3: String = "",
	var rus_pagado_1: String = "",

    @OneToOne
    @PrimaryKeyJoinColumn(name="customer_id", referencedColumnName="credit_id")
    var customer: Customer? = null
    
) {
    @Column(name = "customer_id")
    var customerId: Long = 0
}