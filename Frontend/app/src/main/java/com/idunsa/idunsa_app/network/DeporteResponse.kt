package com.idunsa.idunsa_app.network

data class DeporteResponse(
    val id: Int,
    val nombre: String,
    val jugadores: Int,
    val suplentes: Int,
    val tipo: String
)