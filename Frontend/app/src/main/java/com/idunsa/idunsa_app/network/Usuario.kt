package com.idunsa.idunsa_app.network

data class Usuario(
    val cui: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String,
    val escuela: String,
    val rol: String
)
