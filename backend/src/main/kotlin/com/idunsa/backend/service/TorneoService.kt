package com.idunsa.backend.servicio

import com.idunsa.backend.domain.torneo.*
import com.idunsa.backend.repository.TorneoRepository
import com.idunsa.backend.repository.EventoRepository
import com.idunsa.backend.repository.DeporteRepository
import com.idunsa.backend.dto.DeporteResponseDTO
import com.idunsa.backend.dto.torneo.TorneoRequestDTO
import com.idunsa.backend.dto.torneo.TorneoResponseDTO
import org.springframework.stereotype.Service

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeParseException

@Service
class TorneoService(
    private val torneoRepository: TorneoRepository,
    private val eventoRepository: EventoRepository,
    private val deporteRepository: DeporteRepository

) {

    fun crearTorneo(request: TorneoRequestDTO): TorneoResponseDTO {
        val evento = eventoRepository.findById(request.eventoId)
            .orElseThrow { RuntimeException("Evento con ID ${request.eventoId} no encontrado") }

        val deporte = deporteRepository.findById(request.deporteId)
            .orElseThrow { RuntimeException("Deporte con ID ${request.deporteId} no encontrado") }
        val torneo = Torneo(
            nombre = request.nombre,
            fecha = LocalDate.parse(request.fecha),    
            hora = LocalTime.parse(request.hora),       
            direccion = request.direccion,
            evento = evento,
            deporte = deporte
        )

        val guardado = torneoRepository.save(torneo)
        return convertirAResponseDTO(guardado)
    }
    
    private fun convertirAResponseDTO(torneo: Torneo): TorneoResponseDTO {
        val deporte = torneo.deporte!!

        return TorneoResponseDTO(
            id = torneo.id,
            nombre = torneo.nombre,
            fecha = torneo.fecha.toString(),
            hora = torneo.hora.toString(),
            direccion = torneo.direccion,
            reglamento = torneo.reglamento,
            eventoId = torneo.evento?.id ?: 0L,
            deporte = DeporteResponseDTO(
                id = deporte.id,
                nombre = deporte.nombre,
                jugadores = deporte.jugadores,
                suplentes = deporte.suplentes,
                tipo = deporte.tipo
            )
        )
    }

    fun obtenerTorneosPorEvento(eventoId: Long): List<TorneoResponseDTO> {
        return torneoRepository.findByEventoId(eventoId)
            .map { convertirAResponseDTO(it) }
    }

    fun eliminarTorneo(id: Long) {
        if (!torneoRepository.existsById(id)) {
            throw RuntimeException("Torneo no encontrado")
        }
        torneoRepository.deleteById(id)
    }


    fun actualizarTorneo(id: Long, request: TorneoRequestDTO): TorneoResponseDTO {
        val torneo = torneoRepository.findById(id)
            .orElseThrow { RuntimeException("Torneo no encontrado") }

        val deporte = deporteRepository.findById(request.deporteId)
            .orElseThrow { RuntimeException("Deporte con ID ${request.deporteId} no encontrado") }

        torneo.nombre = request.nombre
        torneo.fecha = LocalDate.parse(request.fecha)
        torneo.hora = LocalTime.parse(request.hora)
        torneo.direccion = request.direccion
        torneo.deporte = deporte

        val actualizado = torneoRepository.save(torneo)
        return convertirAResponseDTO(actualizado)
    }


    fun obtenerTorneoPorId(id: Long): TorneoResponseDTO {
        val torneo = torneoRepository.findById(id)
            .orElseThrow { RuntimeException("Torneo con ID $id no encontrado") }
        return convertirAResponseDTO(torneo)
    }

    fun actualizarReglamento(torneoId: Long, nuevoReglamento: String?): TorneoResponseDTO {
        val torneo = torneoRepository.findById(torneoId)
            .orElseThrow { RuntimeException("Torneo no encontrado") }

        torneo.reglamento = nuevoReglamento
        val actualizado = torneoRepository.save(torneo)
        return convertirAResponseDTO(actualizado)
    }


}
