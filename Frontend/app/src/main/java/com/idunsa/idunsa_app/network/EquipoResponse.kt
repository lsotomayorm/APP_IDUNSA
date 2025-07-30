package com.idunsa.idunsa_app.network

data class EquipoResponse(
    val id: Long,
    val nombre: String,
    val capitan: String,
    val integrantes: List<String>
)
