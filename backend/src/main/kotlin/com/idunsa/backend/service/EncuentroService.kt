package com.idunsa.backend.service

import com.idunsa.backend.domain.encuentro.Encuentro
import com.idunsa.backend.domain.encuentro.EstadoEncuentro
import com.idunsa.backend.domain.equipo.Equipo
import com.idunsa.backend.domain.torneo.Torneo
import com.idunsa.backend.dto.EncuentroUpdateRequest
import com.idunsa.backend.dto.EncuentroUpdateResponse
import com.idunsa.backend.dto.EncuentroResponse
import com.idunsa.backend.repository.EncuentroRepository
import com.idunsa.backend.repository.EquipoRepository
import com.idunsa.backend.repository.TorneoRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class EncuentroService(
    val encuentroRepository: EncuentroRepository,
    val equipoRepository: EquipoRepository,
    val torneoRepository: TorneoRepository
) {

    fun listarPorTorneo(torneoId: Long): List<EncuentroResponse> {
        return encuentroRepository.findByTorneoId(torneoId).map {
            EncuentroResponse(
                id = it.id,
                equipo1 = it.equipo1?.nombre ?: "VACÍO",
                equipo2 = it.equipo2?.nombre ?: "VACÍO",
                ronda = it.ronda,
                nombreRonda = it.nombreRonda,
                score1 = it.score1,
                score2 = it.score2,
                ganador = it.ganador?.nombre ?: "VACÍO",
                fecha = it.fecha.toString(),
                hora = it.hora.format(DateTimeFormatter.ofPattern("HH:mm")),
                estado = it.estado.name
            )
        }
    }

    fun generarEncuentrosPorTorneo(torneoId: Long) {
        check(!encuentroRepository.existsByTorneoId(torneoId)) { "Ya existen encuentros generados para este torneo." }


        val torneo = torneoRepository.findById(torneoId).orElseThrow()
        val equipos = equipoRepository.findByTorneoId(torneoId)

        val cantidadEquipos = equipos.size
        val siguientePotencia = calcularSiguientePotenciaDeDos(cantidadEquipos)
        val cantidadPreliminares = cantidadEquipos - (siguientePotencia / 2)
        val tieneFasePreliminar = cantidadEquipos > 2 && cantidadPreliminares > 0


        val totalRondas = (Math.log(siguientePotencia.toDouble()) / Math.log(2.0)).toInt()
        val totalRondasIncluyendoPreliminar = if (tieneFasePreliminar) totalRondas + 1 else totalRondas
 

        val preliminares = equipos.shuffled().take(cantidadPreliminares * 2)
        val clasificadosDirecto = equipos - preliminares.toSet()

        val encuentrosPreliminares = mutableListOf<Encuentro>()
        var horaActual = torneo.hora
        val fecha = torneo.fecha

        val rondaNombre = obtenerNombreRonda(0, totalRondasIncluyendoPreliminar, tieneFasePreliminar)

        for (i in preliminares.indices step 2) {
            encuentrosPreliminares.add(
                Encuentro(
                    equipo1 = preliminares[i],
                    equipo2 = preliminares[i + 1],
                    ronda = 0,
                    nombreRonda = rondaNombre,
                    fecha = fecha,
                    hora = horaActual,
                    torneo = torneo,
                    estado = EstadoEncuentro.PENDIENTE
                )
            )
            horaActual = horaActual.plusMinutes(20)
        }

        encuentroRepository.saveAll(encuentrosPreliminares)

        val ronda1Equipos = mutableListOf<Equipo?>()

        ronda1Equipos.addAll(clasificadosDirecto)

        repeat(cantidadPreliminares) {
            ronda1Equipos.add(null)
        }

        generarLlaves(ronda1Equipos, 1, torneo, horaActual, totalRondasIncluyendoPreliminar, tieneFasePreliminar)

    }

    fun calcularSiguientePotenciaDeDos(n: Int): Int {
        var potencia = 1
        while (potencia < n) {
            potencia *= 2
        }
        return potencia
    }

    fun generarLlaves(
        equipos: List<Equipo?>,
        ronda: Int,
        torneo: Torneo,
        horaInicio: LocalTime,
        totalRondas: Int,
        tieneFasePreliminar: Boolean
    ): LocalTime {

        if (equipos.size < 2) return horaInicio

        val encuentros = mutableListOf<Encuentro>()
        val fecha = torneo.fecha
        var horaActual = horaInicio

        val nombreRonda = obtenerNombreRonda(ronda, totalRondas, tieneFasePreliminar)

        for (i in equipos.indices step 2) {
            val equipo1 = equipos[i]
            val equipo2 = if (i + 1 < equipos.size) equipos[i + 1] else null

            encuentros.add(
                Encuentro(
                    equipo1 = equipo1,
                    equipo2 = equipo2,
                    ronda = ronda,
                    nombreRonda = nombreRonda,
                    fecha = fecha,
                    hora = horaActual,
                    torneo = torneo,
                    estado = EstadoEncuentro.PENDIENTE
                )
            )
            horaActual = horaActual.plusMinutes(20)
        }


        encuentroRepository.saveAll(encuentros)

        val siguienteRonda = List(encuentros.size) { null }

        return generarLlaves(siguienteRonda, ronda + 1, torneo, horaActual, totalRondas, tieneFasePreliminar)

    }

    companion object {
        private const val FINAL = "Final"
        private const val SEMIFINAL = "Semifinal"
        private const val CUARTOS_DE_FINAL = "Cuartos de Final"
        private const val FASE_PRELIMINAR = "Fase Preliminar"
        private const val VACIO = "VACÍO"
    }

    fun obtenerNombreRonda(index: Int, totalRondas: Int, tieneFasePreliminar: Boolean): String {
        if (!tieneFasePreliminar && index == 0) {
            return FINAL
        }
        val rondasRestantes = if (tieneFasePreliminar) totalRondas - 1 else totalRondas

        if (tieneFasePreliminar && index == 0) {
            return FASE_PRELIMINAR
        }

        if (rondasRestantes >= 5) {
            return when (index) {
                in 1 until rondasRestantes - 3 -> "Ronda $index"
                rondasRestantes - 3 -> CUARTOS_DE_FINAL
                rondasRestantes - 2 -> SEMIFINAL
                rondasRestantes - 1 -> FINAL
                else -> "Ronda $index"
            }
        }

        return when (rondasRestantes) {
            1 -> FINAL
            2 -> when (index) {
                1 -> SEMIFINAL
                2 -> FINAL
                else -> "Ronda $index"
            }
            3 -> when (index) {
                1 -> CUARTOS_DE_FINAL
                2 -> SEMIFINAL
                3 -> FINAL
                else -> "Ronda $index"
            }
            4 -> when (index) {
                1 -> "Ronda 1"
                2 -> CUARTOS_DE_FINAL
                3 -> SEMIFINAL
                4 -> FINAL
                else -> "Ronda $index"
            }
            else -> "Ronda $index"
        }
    }


    fun obtenerEncuentroPorId(id: Long): EncuentroResponse {
        val encuentro = encuentroRepository.findById(id).orElseThrow { 
            throw RuntimeException("Encuentro no encontrado") 
        }

        return EncuentroResponse(
            id = encuentro.id,
            equipo1 = encuentro.equipo1?.nombre ?: "VACÍO",
            equipo2 = encuentro.equipo2?.nombre ?: "VACÍO",
            ronda = encuentro.ronda,
            nombreRonda = encuentro.nombreRonda,
            score1 = encuentro.score1,
            score2 = encuentro.score2,
            ganador = encuentro.ganador?.nombre ?: "VACÍO",
            fecha = encuentro.fecha.toString(),
            hora = encuentro.hora.format(DateTimeFormatter.ofPattern("HH:mm")),
            estado = encuentro.estado.name
        )
    }

    fun actualizarEncuentro(id: Long, request: EncuentroUpdateRequest): EncuentroUpdateResponse {
        val encuentro = encuentroRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Encuentro no encontrado con ID $id")
        }

        request.score1?.let { encuentro.score1 = it }
        request.score2?.let { encuentro.score2 = it }

        encuentro.estado = EstadoEncuentro.valueOf(request.estado)

        request.ganador?.let { nombreEquipo ->
        val equipoGanador = equipoRepository.findByNombre(nombreEquipo)
            ?: throw IllegalArgumentException("No se encontró equipo con nombre $nombreEquipo")
        encuentro.ganador = equipoGanador
        }

        val encuentroGuardado = encuentroRepository.save(encuentro)

        if (encuentroGuardado.estado == EstadoEncuentro.FINALIZADO && encuentroGuardado.ganador != null) {
            avanzarGanadorASiguienteRonda(encuentroGuardado.id)
        }

        return encuentroRepository.save(encuentro).toResponse()
    }

    fun Encuentro.toResponse(): EncuentroUpdateResponse {
        return EncuentroUpdateResponse(
            id = this.id,
            score1 = this.score1,
            score2 = this.score2,
            estado = this.estado.name,
            ganador = this.ganador?.id
        )
    }

    fun avanzarGanadorASiguienteRonda(encuentroId: Long) {
        val encuentro = encuentroRepository.findById(encuentroId)
            .orElseThrow { IllegalArgumentException("Encuentro no encontrado con ID $encuentroId") }

        check(encuentro.estado == EstadoEncuentro.FINALIZADO && encuentro.ganador != null) {
            "El encuentro aún no está finalizado o no tiene un ganador"
        }


        val siguienteRonda = encuentro.ronda + 1

        val encuentrosSiguienteRonda = encuentroRepository
            .findByTorneoIdAndRonda(encuentro.torneo.id, siguienteRonda)

        val encuentroDestino = encuentrosSiguienteRonda.find {
            it.equipo1 == null || it.equipo2 == null
        } ?: throw IllegalStateException("No hay espacio disponible en la ronda siguiente")

        if (encuentroDestino.equipo1 == null) {
            encuentroDestino.equipo1 = encuentro.ganador
        } else {
            encuentroDestino.equipo2 = encuentro.ganador
        }

        encuentroRepository.save(encuentroDestino)
    }


}
