package com.idunsa.backend.service

import com.idunsa.backend.domain.equipo.Equipo
import com.idunsa.backend.repository.EquipoRepository
import com.idunsa.backend.repository.TorneoRepository
import com.idunsa.backend.repository.UsuarioRepository
import com.idunsa.backend.dto.EquipoRequestDTO
import com.idunsa.backend.dto.EquipoResponseDTO
import org.springframework.stereotype.Service

@Service
class EquipoService(
    private val equipoRepository: EquipoRepository,
    private val torneoRepository: TorneoRepository,
    private val usuarioRepository: UsuarioRepository
) {
    fun crearEquipo(dto: EquipoRequestDTO): Equipo {
        val torneo = torneoRepository.findById(dto.torneoId).orElseThrow()
        val capitan = usuarioRepository.findById(dto.capitanId).orElseThrow()

        val integranteIds = dto.integranteIds
        val integrantes = usuarioRepository.findAllById(integranteIds)

        if (integrantes.size != integranteIds.size) {
            throw IllegalArgumentException("Algunos integrantes no existen en el sistema.")
        }

        val equipo = Equipo(
            nombre = dto.nombre,
            torneo = torneo,
            capitan = capitan,
            integrantes = integrantes.toMutableList()
        )

        return equipoRepository.save(equipo)
    }


    fun obtenerEquiposPorTorneo(torneoId: Long): List<EquipoResponseDTO> {
        return equipoRepository.findByTorneoId(torneoId).map {
            EquipoResponseDTO(
                id = it.id,
                nombre = it.nombre,
                capitan = "${it.capitan?.nombre} ${it.capitan?.apellido}",
                integrantes = it.integrantes.map { u -> "${u.nombre} ${u.apellido}" }
            )
        }
    }
}
