package com.idunsa.backend.repository

import com.idunsa.backend.domain.equipo.Equipo
import org.springframework.data.jpa.repository.JpaRepository

interface EquipoRepository : JpaRepository<Equipo, Long> {
    fun findByTorneoId(torneoId: Long): List<Equipo>
    fun findByNombre(nombre: String): Equipo?
}
