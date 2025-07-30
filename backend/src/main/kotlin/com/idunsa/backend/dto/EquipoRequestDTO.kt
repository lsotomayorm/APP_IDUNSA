package com.idunsa.backend.dto

data class EquipoRequestDTO(
    val nombre: String,
    val torneoId: Long,
    val capitanId: Int,
    val integranteIds: List<Int>
)
