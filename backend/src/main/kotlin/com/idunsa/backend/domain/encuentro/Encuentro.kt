package com.idunsa.backend.domain.encuentro

import java.time.LocalDate
import java.time.LocalTime
import com.idunsa.backend.domain.equipo.Equipo
import com.idunsa.backend.domain.torneo.Torneo
import jakarta.persistence.*

@Entity
@Table(name = "encuentros")
class Encuentro() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "equipo1_id")
    var equipo1: Equipo? = null

    @ManyToOne
    @JoinColumn(name = "equipo2_id")
    var equipo2: Equipo? = null

    @Column(nullable = false)
    var ronda: Int = 0

    var nombreRonda: String = ""


    var score1: Float? = null
    var score2: Float? = null

    @ManyToOne
    @JoinColumn(name = "ganador_id")
    var ganador: Equipo? = null

    var fecha: LocalDate = LocalDate.now()
    var hora: LocalTime = LocalTime.now()

    @ManyToOne
    @JoinColumn(name = "torneo_id", nullable = false)
    lateinit var torneo: Torneo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var estado: EstadoEncuentro = EstadoEncuentro.PENDIENTE

    constructor(
        equipo1: Equipo?,
        equipo2: Equipo?,
        ronda: Int,
        nombreRonda: String,
        fecha: LocalDate,
        hora: LocalTime,
        torneo: Torneo,
        estado: EstadoEncuentro = EstadoEncuentro.PENDIENTE
    ) : this() {
        this.equipo1 = equipo1
        this.equipo2 = equipo2
        this.ronda = ronda
        this.nombreRonda = nombreRonda
        this.fecha = fecha
        this.hora = hora
        this.torneo = torneo
        this.estado = estado
    }
}


enum class EstadoEncuentro {
    PENDIENTE,
    EN_CURSO,
    FINALIZADO
}
