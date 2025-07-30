package com.idunsa.backend.controller

import com.idunsa.backend.dto.torneo.TorneoRequestDTO
import com.idunsa.backend.dto.torneo.TorneoResponseDTO
import com.idunsa.backend.servicio.TorneoService
import com.idunsa.backend.domain.torneo.Torneo
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/torneos")
class TorneoController(
    private val torneoService: TorneoService
) {

    @PostMapping
    fun crearTorneo(@RequestBody request: TorneoRequestDTO): ResponseEntity<TorneoResponseDTO> {
        val torneo = torneoService.crearTorneo(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(torneo)
    }

    @GetMapping("/evento/{eventoId}")
    fun obtenerTorneosPorEvento(@PathVariable eventoId: Long): ResponseEntity<List<TorneoResponseDTO>> {
        val torneos = torneoService.obtenerTorneosPorEvento(eventoId)
        return ResponseEntity.ok(torneos)
    }

    
    @DeleteMapping("/{id}")
    fun eliminarTorneo(@PathVariable id: Long): ResponseEntity<Unit> {
        torneoService.eliminarTorneo(id)
        return ResponseEntity.noContent().build()
    }


    @PutMapping("/{id}")
    fun actualizarTorneo(
        @PathVariable id: Long,
        @RequestBody request: TorneoRequestDTO
    ): ResponseEntity<TorneoResponseDTO> {
        val actualizado = torneoService.actualizarTorneo(id, request)
        return ResponseEntity.ok(actualizado)
    }

    @GetMapping("/{id}")
    fun obtenerTorneoPorId(@PathVariable id: Long): ResponseEntity<TorneoResponseDTO> {
        val torneo = torneoService.obtenerTorneoPorId(id)
        return ResponseEntity.ok(torneo)
    }

    @PatchMapping("/{id}/reglamento")
    fun actualizarReglamento(
        @PathVariable id: Long,
        @RequestBody body: Map<String, String?>
    ): ResponseEntity<TorneoResponseDTO> {
        val nuevoReglamento = body["reglamento"]
        val actualizado = torneoService.actualizarReglamento(id, nuevoReglamento)
        return ResponseEntity.ok(actualizado)
    }

}

