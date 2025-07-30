package com.idunsa.backend.dto

data class RegistroRequest(
    val cui: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String,
    val escuela: String
)
