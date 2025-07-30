package com.idunsa.backend.repository

import com.idunsa.backend.domain.torneo.Torneo
import org.springframework.data.jpa.repository.JpaRepository

interface TorneoRepository : JpaRepository<Torneo, Long> {
    fun findByEventoId(eventoId: Long): List<Torneo>
    fun findByNombreContainingIgnoreCase(nombre: String): List<Torneo>
}
