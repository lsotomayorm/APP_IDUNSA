package com.idunsa.backend.controller

import com.idunsa.backend.dto.EquipoRequestDTO
import com.idunsa.backend.service.EquipoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/equipos")
class EquipoController(
    private val equipoService: EquipoService
) {
    @PostMapping
    fun crearEquipo(@RequestBody dto: EquipoRequestDTO): ResponseEntity<Any> {
        return try {
            val equipo = equipoService.crearEquipo(dto)
            ResponseEntity.ok(equipo)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }


    @GetMapping("/torneo/{torneoId}")
    fun listarPorTorneo(@PathVariable torneoId: Long) =
        ResponseEntity.ok(equipoService.obtenerEquiposPorTorneo(torneoId))
}
