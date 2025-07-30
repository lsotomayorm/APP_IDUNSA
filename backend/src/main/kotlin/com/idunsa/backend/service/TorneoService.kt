import com.idunsa.backend.domain.torneo.Torneo
import com.idunsa.backend.dto.DeporteResponseDTO
import com.idunsa.backend.dto.torneo.TorneoRequestDTO
import com.idunsa.backend.dto.torneo.TorneoResponseDTO
import com.idunsa.backend.repository.DeporteRepository
import com.idunsa.backend.repository.EventoRepository
import com.idunsa.backend.repository.TorneoRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Service
class TorneoService(
    private val torneoRepository: TorneoRepository,
    private val eventoRepository: EventoRepository,
    private val deporteRepository: DeporteRepository
) {

    companion object {
        private const val MENSAJE_TORNEO_NO_ENCONTRADO = "Torneo no encontrado"
        private const val MENSAJE_EVENTO_NO_ENCONTRADO = "Evento no encontrado"
        private const val MENSAJE_DEPORTE_NO_ENCONTRADO = "Deporte no encontrado"
    }

    fun crearTorneo(request: TorneoRequestDTO): TorneoResponseDTO {
        val evento = eventoRepository.findById(request.eventoId)
            .orElseThrow { RuntimeException("$MENSAJE_EVENTO_NO_ENCONTRADO con ID ${request.eventoId}") }

        val deporte = deporteRepository.findById(request.deporteId)
            .orElseThrow { RuntimeException("$MENSAJE_DEPORTE_NO_ENCONTRADO con ID ${request.deporteId}") }

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
        val deporte = torneo.deporte ?: throw RuntimeException(MENSAJE_DEPORTE_NO_ENCONTRADO)

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
        require(torneoRepository.existsById(id)) { MENSAJE_TORNEO_NO_ENCONTRADO }
        torneoRepository.deleteById(id)
    }

    fun actualizarTorneo(id: Long, request: TorneoRequestDTO): TorneoResponseDTO {
        val torneo = torneoRepository.findById(id)
            .orElseThrow { RuntimeException(MENSAJE_TORNEO_NO_ENCONTRADO) }

        val deporte = deporteRepository.findById(request.deporteId)
            .orElseThrow { RuntimeException("$MENSAJE_DEPORTE_NO_ENCONTRADO con ID ${request.deporteId}") }

        torneo.apply {
            nombre = request.nombre
            fecha = LocalDate.parse(request.fecha)
            hora = LocalTime.parse(request.hora)
            direccion = request.direccion
            this.deporte = deporte
        }

        val actualizado = torneoRepository.save(torneo)
        return convertirAResponseDTO(actualizado)
    }

    fun obtenerTorneoPorId(id: Long): TorneoResponseDTO {
        val torneo = torneoRepository.findById(id)
            .orElseThrow { RuntimeException("$MENSAJE_TORNEO_NO_ENCONTRADO con ID $id") }
        return convertirAResponseDTO(torneo)
    }

    fun actualizarReglamento(torneoId: Long, nuevoReglamento: String?): TorneoResponseDTO {
        val torneo = torneoRepository.findById(torneoId)
            .orElseThrow { RuntimeException(MENSAJE_TORNEO_NO_ENCONTRADO) }

        torneo.reglamento = nuevoReglamento
        val actualizado = torneoRepository.save(torneo)
        return convertirAResponseDTO(actualizado)
    }
}
