package com.idunsa.backend.repository

import com.idunsa.backend.domain.escuela.Escuela
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface EscuelaRepository : JpaRepository<Escuela, Int>{
    @Query("SELECT e FROM Escuela e ORDER BY e.nombre ASC")
    fun findAllOrderByNombreAsc(): List<Escuela>
}