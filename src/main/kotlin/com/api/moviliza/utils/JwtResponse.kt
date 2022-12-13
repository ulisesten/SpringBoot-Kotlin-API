package com.api.moviliza.utils

import com.api.moviliza.model.Customer

class JwtResponse(token: String, user: Customer?) {
    private val user: Customer
    private val token:String

    init {
        this.user = user!!
        this.token = token
    }

    fun getUser(): Customer {
        return user
    }

    fun getToken(): String {
        return token
    }
}