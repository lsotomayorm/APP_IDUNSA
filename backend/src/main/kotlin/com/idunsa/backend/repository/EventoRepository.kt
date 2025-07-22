package com.idunsa.backend.repository

import com.idunsa.backend.domain.evento.Evento
import org.springframework.data.jpa.repository.JpaRepository

interface EventoRepository : JpaRepository<Evento, Long>
