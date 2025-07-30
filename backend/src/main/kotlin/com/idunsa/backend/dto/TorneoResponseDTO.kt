package com.idunsa.backend.dto.torneo

import java.time.LocalDate
import com.idunsa.backend.domain.torneo.Torneo
import com.idunsa.backend.dto.DeporteResponseDTO

data class TorneoResponseDTO(
    val id: Long,
    val nombre: String,
    val fecha: String,
    val hora: String,
    val direccion: String,
    val eventoId: Long,
    val deporte: DeporteResponseDTO,
    val reglamento: String?
) 
