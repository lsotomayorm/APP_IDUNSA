package com.idunsa.backend.dto

data class DeporteResponseDTO(
    val id: Int,
    val nombre: String,
    val jugadores: Int,
    val suplentes: Int,
    val tipo: String
)
