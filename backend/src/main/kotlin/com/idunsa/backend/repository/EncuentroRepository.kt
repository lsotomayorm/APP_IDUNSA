package com.idunsa.backend.repository

import com.idunsa.backend.domain.encuentro.Encuentro
import org.springframework.data.jpa.repository.JpaRepository

interface EncuentroRepository : JpaRepository<Encuentro, Long> {
    fun findByEquipo1IdOrEquipo2Id(equipo1Id: Long, equipo2Id: Long): List<Encuentro>
    fun findByTorneoId(torneoId: Long): List<Encuentro>
    fun existsByTorneoId(torneoId: Long): Boolean
    fun findByTorneoIdAndRonda(torneoId: Long, ronda: Int): List<Encuentro>



}
