package com.idunsa.backend.dto

data class EquipoResponseDTO(
    val id: Long,
    val nombre: String,
    val capitan: String,
    val integrantes: List<String>
)
