package com.api.moviliza.utils

class JwtRequest {
    var email: String? = null
    var password: String? = null

    constructor()
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }
}