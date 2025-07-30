package com.idunsa.backend.service

import com.idunsa.backend.dto.DeporteResponseDTO
import com.idunsa.backend.repository.DeporteRepository
import org.springframework.stereotype.Service

@Service
class DeporteService(
    private val deporteRepository: DeporteRepository
) {

    fun obtenerTodos(): List<DeporteResponseDTO> {
        return deporteRepository.findAll().map {
            DeporteResponseDTO(
                id = it.id,
                nombre = it.nombre,
                jugadores = it.jugadores,
                suplentes = it.suplentes,
                tipo = it.tipo
            )
        }
    }

    fun obtenerPorNombre(nombre: String): DeporteResponseDTO? {
        val deporte = deporteRepository.findByNombre(nombre) ?: return null
        return DeporteResponseDTO(
            id = deporte.id,
            nombre = deporte.nombre,
            jugadores = deporte.jugadores,
            suplentes = deporte.suplentes,
            tipo = deporte.tipo
        )
    }
}
