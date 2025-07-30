package com.idunsa.backend.repository

import com.idunsa.backend.domain.deporte.Deporte
import org.springframework.data.jpa.repository.JpaRepository

interface DeporteRepository : JpaRepository<Deporte, Int> {
    fun findByNombre(nombre: String): Deporte?
}
