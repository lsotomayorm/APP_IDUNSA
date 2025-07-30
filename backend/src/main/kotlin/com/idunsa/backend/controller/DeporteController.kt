package com.idunsa.backend.controller

import com.idunsa.backend.dto.DeporteResponseDTO
import com.idunsa.backend.service.DeporteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/deportes")
class DeporteController(
    private val deporteService: DeporteService
) {

    @GetMapping
    fun obtenerDeportes(): List<DeporteResponseDTO> {
        return deporteService.obtenerTodos()
    }

    @GetMapping("/buscar")
    fun obtenerDeportePorNombre(@RequestParam nombre: String): ResponseEntity<DeporteResponseDTO> {
        val deporte = deporteService.obtenerPorNombre(nombre)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(deporte)
    }
}
