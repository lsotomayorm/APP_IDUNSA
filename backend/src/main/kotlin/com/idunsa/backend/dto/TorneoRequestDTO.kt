package com.idunsa.backend.dto.torneo
import java.time.LocalDate
import java.time.LocalTime

data class TorneoRequestDTO(
    val nombre: String,
    val fecha: String,
    val hora: String,
    val direccion: String,
    val eventoId: Long,
    val deporteId: Int 
)
