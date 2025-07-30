package com.idunsa.backend.controller

import com.idunsa.backend.dto.EncuentroRequest
import com.idunsa.backend.dto.EncuentroResponse
import com.idunsa.backend.dto.EncuentroUpdateRequest
import com.idunsa.backend.dto.EncuentroUpdateResponse
import com.idunsa.backend.service.EncuentroService
import com.idunsa.backend.domain.encuentro.Encuentro
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/encuentros")
class EncuentroController(private val service: EncuentroService) {

    @GetMapping("/torneo/{id}")
    fun listarPorTorneo(@PathVariable id: Long): List<EncuentroResponse> {
        return service.listarPorTorneo(id)
    }

    @PostMapping("/torneo/{torneoId}/generar")
    fun generarEncuentrosPorTorneo(@PathVariable torneoId: Long): ResponseEntity<String> {
        service.generarEncuentrosPorTorneo(torneoId)
        return ResponseEntity.ok("Encuentros generados para el torneo $torneoId")
    }

    
    @GetMapping("/{id}")
    fun obtenerEncuentroPorId(@PathVariable id: Long): ResponseEntity<EncuentroResponse> {
        val encuentro = service.obtenerEncuentroPorId(id)
        return ResponseEntity.ok(encuentro)
    }

    @PutMapping("/{id}")
    fun actualizarEncuentro(
        @PathVariable id: Long,
        @RequestBody encuentroRequest: EncuentroUpdateRequest
    ): ResponseEntity<EncuentroUpdateResponse> {
        val encuentroActualizado = service.actualizarEncuentro(id, encuentroRequest)
        return ResponseEntity.ok(encuentroActualizado)
    }

}
