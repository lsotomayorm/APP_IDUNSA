package com.idunsa.backend.domain.evento

import com.idunsa.backend.domain.torneo.Torneo
import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Evento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nombre: String = "",

    val fechaInicio: LocalDate = LocalDate.now(),
    val fechaFin: LocalDate = LocalDate.now(),

    @OneToMany(mappedBy = "evento", cascade = [CascadeType.ALL], orphanRemoval = true)
    val torneos: List<Torneo> = emptyList()
)
