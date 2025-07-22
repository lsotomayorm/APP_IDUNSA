package com.idunsa.backend.domain.torneo

import com.idunsa.backend.domain.evento.Evento
import com.idunsa.backend.domain.deporte.Deporte
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Torneo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var nombre: String = "",
    var fecha: LocalDate = LocalDate.now(),
    var hora: LocalTime = LocalTime.now(),
    var direccion: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    var evento: Evento? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deporte_id", nullable = false)
    var deporte: Deporte? = null,

    @Column(columnDefinition = "TEXT")
    var reglamento: String? = null

)
