package com.idunsa.backend.dto.evento

import java.time.LocalDate

data class EventoResponseDTO(
    val id: Long,
    val nombre: String,
    val fechaInicio: String,
    val fechaFin: String
)
