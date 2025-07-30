package com.idunsa.backend.dto.evento

import java.time.LocalDate

data class EventoRequestDTO(
    val nombre: String,
    val fechaInicio: String,
    val fechaFin: String
)