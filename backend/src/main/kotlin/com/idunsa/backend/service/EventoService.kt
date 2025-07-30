package com.idunsa.backend.servicio

import com.idunsa.backend.domain.evento.Evento
import com.idunsa.backend.dto.evento.EventoRequestDTO
import com.idunsa.backend.dto.evento.EventoResponseDTO
import com.idunsa.backend.repository.EventoRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Service
class EventoService(private val eventoRepository: EventoRepository) {

    fun listarEventos(): List<EventoResponseDTO> {
        return try {
            eventoRepository.findAll().map {
                EventoResponseDTO(
                    id = it.id,
                    nombre = it.nombre,
                    fechaInicio = it.fechaInicio.toString(),
                    fechaFin = it.fechaFin.toString()
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun crearEvento(dto: EventoRequestDTO): EventoResponseDTO {
        val fechaInicioParsed = LocalDate.parse(dto.fechaInicio)
        val fechaFinParsed = LocalDate.parse(dto.fechaFin)

        val evento = Evento(
            nombre = dto.nombre,
            fechaInicio = fechaInicioParsed,
            fechaFin = fechaFinParsed,
            torneos = emptyList()
        )

        val guardado = eventoRepository.save(evento)

        return EventoResponseDTO(
            id = guardado.id,
            nombre = guardado.nombre,
            fechaInicio = guardado.fechaInicio.toString(),
            fechaFin = guardado.fechaFin.toString()
        )
    }

    fun actualizarEvento(id: Long, dto: EventoRequestDTO): EventoResponseDTO {
        val eventoExistente = eventoRepository.findById(id).orElseThrow {
            IllegalArgumentException("Evento con ID $id no encontrado")
        }

        val eventoActualizado = eventoExistente.copy(
            nombre = dto.nombre,
            fechaInicio = LocalDate.parse(dto.fechaInicio),
            fechaFin = LocalDate.parse(dto.fechaFin)
        )

        val guardado = eventoRepository.save(eventoActualizado)

        return EventoResponseDTO(
            id = guardado.id,
            nombre = guardado.nombre,
            fechaInicio = guardado.fechaInicio.toString(),
            fechaFin = guardado.fechaFin.toString()
        )
    }


    fun eliminarEvento(id: Long) {
        if (!eventoRepository.existsById(id)) {
            throw IllegalArgumentException("Evento con ID $id no existe")
        }
        eventoRepository.deleteById(id)
    }


}
